package com.techelevator.tebucks.controller;

import com.techelevator.tebucks.client.services.LoggingService;
import com.techelevator.tebucks.dao.AccountDao;
import com.techelevator.tebucks.dao.TransferDao;
import com.techelevator.tebucks.exception.DaoException;
import com.techelevator.tebucks.model.*;
import com.techelevator.tebucks.security.dao.JdbcUserDao;
import com.techelevator.tebucks.security.dao.UserDao;
import com.techelevator.tebucks.security.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/api/")
@RestController
public class AccountController {

	private final JdbcUserDao userDao;
	private final LoggingService loggingService;
	private final AccountDao accountDao;
	private final TransferDao transferDao;
	String logDescription = null;

	public AccountController (LoggingService loggingService, AccountDao accountDao, TransferDao transferDao, JdbcUserDao userDao) {
		this.loggingService = loggingService;
		this.accountDao = accountDao;
		this.transferDao = transferDao;
		this.userDao = userDao;
	}

	@GetMapping(path = "users")
	public List<User> showAllUsers () {
		List<User> users = userDao.getAllUsers();

		if (users == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Users could not be found");
		} else {
			return users;
		}
	}

	@GetMapping(path = "account/balance")
	public Account showAccountBalance(Principal principal) {
		String username = principal.getName();
		Account account = accountDao.getAccountByUsername(username);

		if (account == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User balance could not be found");
		} else {
			return account;
		}
	}

	@GetMapping(path = "account/transfers/")
	public List<Transfer> showTransfers(Principal principal) {
		String username = principal.getName();
		Account account = accountDao.getAccountByUsername(username);

		if (account == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfers for this account could not be found");
		} else {
			return transferDao.getTransfersByAccount(account.getAccountId());
		}
	}

	@GetMapping(path = "transfers/{id}")
	public Transfer showTransferById(@PathVariable int id) {
		return transferDao.getTransferById(id);
	}

	@PutMapping(path = "transfers/{id}/status")
	public Transfer updateTransferStatus(@PathVariable int id, @RequestBody TransferStatusUpdateDto newTransferStatus) {
		Transfer transfer = transferDao.getTransferById(id);
		String status = newTransferStatus.getTransferStatus();
		if (status.equals("Approved")) {
			transferTeBucks(transfer);
		}
		transfer.setTransferStatus(status);
		return transferDao.updateTransfer(transfer);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(path = "transfers")
	public Transfer newTransfer(@Valid @RequestBody NewTransferDto transferDto) {
		if (transferDto == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong with your transfer");
		}

		Transfer newTransfer = transferDao.createTransfer(transferDto);

		if (checkTransferAmount(transferDto) && (transferDto.getTransferType().equals("Send"))) {
			transferTeBucks(newTransfer);
		};
//
		return newTransfer;
	}

	private boolean checkTransferAmount(NewTransferDto transferDto) {
		if (transferDto.getAmount() <= 0) {
			logDescription = "Transfer must be over $0";
			loggingService.logTransaction(makeTransferLoggable(transferDto));
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transfer Amount cannot be zero or negative.");
		}
		if (transferDto.getAmount() > accountDao.getAccountByUserId(transferDto.getUserFrom()).getBalance()) {
			logDescription = "Transfer will exceed balance by $" + (transferDto.getAmount() - accountDao.getAccountById(transferDto.getUserFrom()).getBalance());
			loggingService.logTransaction(makeTransferLoggable(transferDto));
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transfer amount exceeds account balance.");
		}
		if (transferDto.getUserFrom() == transferDto.getUserTo()) {
			logDescription = "User tried transferring money to themself";
			loggingService.logTransaction(makeTransferLoggable(transferDto));
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot transfer or request money from the same to and from accounts");
		}
		return true;
	}

//	@ResponseStatus(HttpStatus.ACCEPTED)
	private boolean transferTeBucks(Transfer transfer) {
		double amount = transfer.getAmount();
		accountDao.adjustBalance(amount, transfer.getToUserId());
		accountDao.adjustBalance(-amount, transfer.getFromUserId());
		return true;
	}

	public LogDto makeTransferLoggable(NewTransferDto newTransferDto) {
		LogDto logDto = new LogDto();
		User fromUser = new User();
		fromUser = userDao.getUserById(newTransferDto.getUserFrom());
		String fromUsername = fromUser.getUsername();
		User toUser = new User();
		toUser = userDao.getUserById(newTransferDto.getUserTo());
		String toUsername = toUser.getUsername();

		logDto.setDescription(logDescription);
		logDto.setUsernameFrom(fromUsername);
		logDto.setUsername_to(toUsername);
		logDto.setAmount(newTransferDto.getAmount());

		return logDto;
	}

}
