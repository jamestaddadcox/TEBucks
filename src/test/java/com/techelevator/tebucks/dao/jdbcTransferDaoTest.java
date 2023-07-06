package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.exception.DaoException;
import com.techelevator.tebucks.model.Account;
import com.techelevator.tebucks.model.NewTransferDto;
import com.techelevator.tebucks.model.Transfer;
import net.minidev.json.writer.FakeMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class jdbcTransferDaoTest extends BaseDaoTests{
    protected static final Transfer TRANSFER_1 = new Transfer(1, 2, 3, "Request", 250, "Pending", null, null);
    protected static final Transfer TRANSFER_2 = new Transfer(2, 1, 2, "Request", 400, "Pending", null, null);

    private JdbcTransferDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferDao(jdbcTemplate);
    }

    @Test
    public void getTransfersByAccount_given_correct_account() {
        List <Transfer> listOfTransfers = sut.getTransfersByAccount(1);
        Assert.assertEquals(1, listOfTransfers.size());
        Assert.assertEquals(TRANSFER_1, listOfTransfers.get(0));
    }
    @Test
    public void getTransfersByFAKEAccount_returns_empty_or_null() {
        List <Transfer> FAKElistOfTransfers = sut.getTransfersByAccount(65);
        Assert.assertEquals(0, FAKElistOfTransfers.size());
    }
    @Test
    public void getTransferById_returns_correct_transfer() {
        Transfer user3account = sut.getTransferById(1);
        Assert.assertEquals(TRANSFER_1, user3account);
    }
    @Test
    public void getTransferByFAKEId_returns_FALSE_given_FAKE_id() {
        Transfer fakeUserAccount = sut.getTransferById(99);
        Assert.assertNull(fakeUserAccount);
    }
    @Test
    public void createTransfer_returns_true() {
        Transfer transfer = sut.createTransfer(new NewTransferDto(2, 1, "Request", 400 ));

        TRANSFER_2.setTransferId(transfer.getTransferId());

        Assert.assertEquals(TRANSFER_2, transfer);
    }
    @Test(expected = DaoException.class)
    public void createTransfer_returns_false() {
        Transfer transfer = sut.createTransfer(new NewTransferDto(2, 4, "Request", 900));
    }
    @Test
    public void updateTransfer_updates_given_valid_stuff() {
        Transfer transfer = sut.updateTransfer(new Transfer(1, 2, 3, "Request", 250, "Pending", null, null));
        Assert.assertEquals(TRANSFER_1, transfer);
    }
    @Test(expected = DaoException.class)
    public void updateTransfer_FAILS_to_update_given_valid_stuff() {
        Transfer transfer = sut.updateTransfer(new Transfer(1, 2, 9998, "Request", 250, "Pending", null, null));

    }

}