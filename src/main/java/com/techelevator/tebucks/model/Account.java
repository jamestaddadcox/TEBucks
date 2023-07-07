package com.techelevator.tebucks.model;

import java.util.Objects;

public class Account {

    private int accountId;
    private int userId;
    private double balance;

    public Account() {

    }

    public Account(int account_id, int user_id, double balance) {
        this.accountId = account_id;
        this.userId = user_id;
        this.balance = balance;
    }
    public int getAccountId() {
        return accountId;
    }
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId == account.accountId &&
                userId == account.userId &&
                Objects.equals(balance, account.balance);

    }
    @Override
    public int hashCode() {
        return Objects.hash(accountId, userId, balance);
    }
    @Override
    public String toString() {
        return "Account{" +
                "user_id ='" + userId + '\'' +
                ", balance ='" + balance + ", account_id = '" + accountId +
                '}';
    }
}
