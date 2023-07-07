package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.Account;

public interface AccountDao {

    Account getAccountByUsername(String username);

    Account getAccountById(int id);

    Account getAccountByUserId(int id);

    boolean adjustBalance(double newBalance, int userId);


}
