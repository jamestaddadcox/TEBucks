package com.techelevator.tebucks.dao;

public interface TransferDao {

    Transfer getTransferById(int transferId);

    List<Transfer> getTransfersByAccount(int accountId);

    Transfer createTransfer(NewTransferDto transferDto);

    Transfer updateTransfer(Transfer transfer);
}
