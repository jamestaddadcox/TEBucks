package com.techelevator.tebucks.dao;

public interface AccountDao {

    Account getAccountByUsername(String username);

    Account getAccountById(int id);


}
