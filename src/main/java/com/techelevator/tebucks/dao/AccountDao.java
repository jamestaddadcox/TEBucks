package com.techelevator.tebucks.dao;

public interface AccountDao {

    Account getAccountByUsername(Username username);

    Account getAccountById(int id);


}
