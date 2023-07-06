package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.Account;
import com.techelevator.tebucks.security.dao.JdbcUserDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.annotation.Id;
import org.springframework.jdbc.core.JdbcTemplate;

public class jdbcAccountDaoTest extends BaseDaoTests{
    protected static final Account ACCOUNT_1 = new Account(1, 3, 500 );
    protected static final Account ACCOUNT_2 = new Account(2, 2, 200 );


    private JdbcAccountDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);
    }
    @Test
    public void getAccountByUsername_returns_True_given_account_1() {
        Account user3account = sut.getAccountByUsername("user3");
        Assert.assertEquals(ACCOUNT_1, user3account);
    }
    @Test
    public void getAccountByFAKEUsername_returns_false_given_FAKE_account() {
        Account userFAKEaccount = sut.getAccountByUsername("userFAKE");
        Assert.assertNull(userFAKEaccount);
    }
    @Test
    public void getAccountById_returns_true_given_correct_id() {
        Account user1account = sut.getAccountById(1);
        Assert.assertEquals(ACCOUNT_1, user1account);
    }
    @Test
    public void getAccountByFAKEId_returns_false_given_FAKE_id() {
        Account userFAKEaccount = sut.getAccountById(6);
        Assert.assertNull(userFAKEaccount);
    }


}

