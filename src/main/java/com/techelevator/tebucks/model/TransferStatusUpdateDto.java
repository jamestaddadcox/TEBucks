package com.techelevator.tebucks.model;

import javax.validation.constraints.AssertTrue;

public class TransferStatusUpdateDto {
    private String transferStatus;


    public TransferStatusUpdateDto() {


    }
    public TransferStatusUpdateDto(String transferStatus) {
        this.transferStatus = transferStatus;
    }
    @AssertTrue
    public boolean isTransferStatusUpdateValid() {
        if (transferStatus == null) {
            return false;
        }
        if (transferStatus == "Pending" || transferStatus == "Rejected" || transferStatus == "Approved") {
            return true;
        } else {
            return false;
        }
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }
}
