package com.techelevator.tebucks.controller;

import com.techelevator.tebucks.client.services.LoggingService;
import com.techelevator.tebucks.dao.AccountDao;
import com.techelevator.tebucks.dao.TransferDao;
import com.techelevator.tebucks.model.*;
import com.techelevator.tebucks.security.dao.JdbcUserDao;
import com.techelevator.tebucks.security.dao.UserDao;
import com.techelevator.tebucks.security.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
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
	private final UserDao userDao;

	public AccountController (LoggingService loggingService, AccountDao accountDao, TransferDao transferDao, UserDao userDao) {
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
	public Transfer updateTransferStatus(@PathVariable int id, TransferStatusUpdateDto newTransferStatus) {
		Transfer transfer = transferDao.getTransferById(id);
		String status = newTransferStatus.getTransferStatus();
		transfer.setTransferStatus(status);
		return transferDao.updateTransfer(transfer);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(path = "transfers")
	public Transfer newTransfer(@Valid @RequestBody NewTransferDto newTransfer) {
		User user = new User();
		user = userDao.getUserById(newTransfer.getUserFrom());
		if (newTransfer == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong with your transfer");
		}

		if (newTransfer.getAmount() > 1000) {
			loggingService.logTransaction(makeTransferLoggable(newTransfer));
			return transferDao.createTransfer(newTransfer);
		}

		if (newTransfer.getAmount() > 1000 && user.)
		return transferDao.createTransfer(newTransfer);
	}

	public LogDto makeTransferLoggable(NewTransferDto newTransferDto) {
		LogDto logDto = new LogDto();
		User fromUser = new User();
		fromUser = userDao.getUserById(newTransferDto.getUserFrom());
		String fromUsername = fromUser.getUsername();
		User toUser = new User();
		toUser = userDao.getUserById(newTransferDto.getUserTo());
		String toUsername = toUser.getUsername();


		logDto.setDescription("Transfer is greater than $1000 by $" + (newTransferDto.getAmount() - 1000.00));
		logDto.setUsernameFrom(fromUsername);
		logDto.setUsername_to(toUsername);
		logDto.setAmount(newTransferDto.getAmount());

		return logDto;
	}
}
