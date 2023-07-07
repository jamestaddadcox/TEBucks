package com.techelevator.tebucks.model;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Positive;

public class NewTransferDto {
    private int userFrom;
    private int userTo;
    private String transferType;
    @Positive
    private double amount;
    @AssertTrue
    private boolean isValidTransferTYpe() {
        if (transferType == null) {
            return false;
        }
        if (transferType.equals("Send")  || transferType.equals("Request") ) {
            return true;
        } else {
            return false;
        }
    }


    public NewTransferDto() {

    }

    public NewTransferDto(int userFrom, int userTo, String transferType, double amount) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.transferType = transferType;
        this.amount = amount;
    }

    public int getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(int userFrom) {
        this.userFrom = userFrom;
    }

    public int getUserTo() {
        return userTo;
    }

    public void setUserTo(int userTo) {
        this.userTo = userTo;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


}

