package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.NewTransferDto;
import com.techelevator.tebucks.model.Transfer;

import java.util.List;

public interface TransferDao {

    Transfer getTransferById(int transferId);

    List<Transfer> getTransfersByAccount(int accountId);

    Transfer createTransfer(NewTransferDto transferDto);

    Transfer updateTransfer(Transfer transfer);
}
