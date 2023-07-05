package com.techelevator.tebucks.controller;

import com.techelevator.tebucks.dao.AccountDao;
import com.techelevator.tebucks.dao.TransferDao;
import com.techelevator.tebucks.model.NewTransferDto;
import com.techelevator.tebucks.model.Transfer;
import com.techelevator.tebucks.security.dao.JdbcUserDao;
import com.techelevator.tebucks.security.dao.UserDao;
import com.techelevator.tebucks.security.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequestMapping(path = "/api/")
@RestController
public class AccountController {

	private final AccountDao accountDao;
	private final TransferDao transferDao;
	private final UserDao userDao;


	@PreAuthorize("isAuthenticated()")
	@GetMapping(path = "users")
	public List<User> showAllUsers () {
		List<User> users = userDao.getAllUsers();

		if (users == null) {
			return new ArrayList<>();
		} else {
			return users;
		}
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("isAuthenticated()")
	@PostMapping(path = "transfers")
	public Transfer newTransfer(@Valid @RequestBody NewTransferDto newTransfer) {
		return transferDao.createTransfer(newTransfer);
	}


}
