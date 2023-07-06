package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.exception.DaoException;
import com.techelevator.tebucks.model.NewTransferDto;
import com.techelevator.tebucks.model.Transfer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transfer getTransferById(int transferId) {
        Transfer transfer = null;
        String sql = "select transfer_id, to_user_id, from_user_id, type, amount, status " +
                "from transfer where transfer_id = ?;";
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, transferId);
            if (rowSet.next()) {
                transfer = mapRowToTransfer(rowSet);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return transfer;
    }

    @Override
    public List<Transfer> getTransfersByAccount(int accountId) {
        List<Transfer> transferList = new ArrayList<>();
        String sql = "select transfer_id, to_user_id, from_user_id, type, amount, status " +
                "from transfer join account " +
                "on (user_id = to_user_id) " +
                "where account_id = ? " +
                "union select transfer_id, to_user_id, from_user_id, type, amount, status " +
                "from transfer join account " +
                "on (user_id = from_user_id)" +
                "where account_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId, accountId);
            while (results.next()) {
                Transfer transfer = mapRowToTransfer(results);
                transferList.add(transfer);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return transferList;
    }

    @Override
    public Transfer createTransfer(NewTransferDto transferDto) {
        if (transferDto.getAmount() <= 0) throw new DaoException("Transfer Amount cannot be zero or negative.");
        Transfer newTransfer = null;
        String sql = "insert into transfer (to_user_id, from_user_id, type, amount, status) values (?, ?, ?, ?, ?) returning transfer_id;";
        String status = null;
        if (transferDto.getTransferType().equals("Send")) {
            status = "Approved";
        } else if (transferDto.getTransferType().equals("Request")) {
            status = "Pending";
        }
        try {
            int transferId = jdbcTemplate.queryForObject(sql, int.class, transferDto.getUserTo(), transferDto.getUserFrom(), transferDto.getTransferType(), transferDto.getAmount(), status);
            newTransfer = getTransferById(transferId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newTransfer;
    }

    @Override
    public Transfer updateTransfer(Transfer transfer) {
        Transfer updatedTransfer = null;
        String sql = "update transfer set to_user_id = ?, from_user_id = ?, type = ?, amount = ?, status = ? where transfer_id = ?;";
        try {
            int rowsAffected = jdbcTemplate.update(sql, transfer.getToUserId(), transfer.getFromUserId(), transfer.getTransferType(), transfer.getAmount(), transfer.getTransferStatus());
            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            }
            updatedTransfer = getTransferById(transfer.getTransferId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedTransfer;
    }

    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rowSet.getInt("transfer_id"));
        transfer.setToUserId(rowSet.getInt("to_user_id"));
        transfer.setFromUserId(rowSet.getInt("from_user_id"));
        transfer.setTransferType(rowSet.getString("type"));
        transfer.setAmount(rowSet.getDouble("amount"));
        transfer.setTransferStatus(rowSet.getString("status"));
        return transfer;
    }

}
