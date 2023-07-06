package com.techelevator.tebucks.model;

import com.techelevator.tebucks.security.model.User;

import java.util.Objects;

public class Transfer {
    private int transferId;
    private int toUserId;
    private int fromUserId;
    private String transferType;
    private double amount;
    private String transferStatus;
    private User userFrom;
    private User userTo;

    public Transfer() {

    }

    public Transfer(int transfer_id, int to_user_id, int from_user_id, String transferType, double amount, String transferStatus, User userFrom, User userTo) {
        this.transferId = transfer_id;
        this.toUserId = to_user_id;
        this.fromUserId = from_user_id;
        this.transferType = transferType;
        this.amount = amount;
        this.transferStatus = transferStatus;
        this.userFrom = userFrom;
        this.userTo = userTo;

    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public User getUserTo() {
        return userTo;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
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

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatusUpdateDto) {
        this.transferStatus = transferStatusUpdateDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return transferId == transfer.transferId &&
                toUserId == transfer.toUserId &&
                fromUserId == transfer.fromUserId &&
                Objects.equals(transferType, transfer.transferType) &&
                Objects.equals(amount, transfer.amount) &&
                Objects.equals(transferStatus, transfer.transferStatus) &&
                Objects.equals(userFrom, transfer.userFrom) &&
                Objects.equals(userTo, transfer.userTo);


    }
    public int hashCode() {
        return Objects.hash(transferId, toUserId, fromUserId, transferType, amount, transferStatus, userFrom, userTo);
    }
    public String toString() {
        return "Transfer{" +
                "transfer_id ='" + transferId + '\'' +
                ", to_user_id ='" + toUserId + '\'' +
                ", from_user_id ='" + fromUserId + '\'' +
                ", type ='" + transferType + '\'' +
                ", amount ='" + amount + '\'' +
                ", status =" + transferStatus + '\'' +
                ", userFrom =" + userFrom + '\'' +
                ", userTo =" + userTo +
                '}';
    }

}
