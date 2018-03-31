package service.bank;

import database.DBConnectionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.bank.AccountRepository;
import repository.bank.AccountRepositoryMySQL;
import repository.bank.ClientRepository;
import repository.bank.ClientRepositoryMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;

import java.sql.Connection;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class AccountTransferServiceMySQLTest {
    private static AccountTransferService accountTransferService;
    private static ClientRepository clientRepository;
    private static AccountRepository accountRepository;

    @Before
    public void setUp() throws Exception {
        Connection connection =new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        clientRepository = new ClientRepositoryMySQL(connection);
        accountRepository =new AccountRepositoryMySQL(connection,clientRepository);
        accountTransferService = new AccountTransferServiceMySQL(accountRepository);
    }

    @After
    public void tearDown() throws Exception {
        accountTransferService.moneyTransfer(2L,1L,100);
    }


    @Test
    public void moneyTransfer() {
        Assert.assertTrue(accountTransferService.moneyTransfer(1L,2L,100)
        .getResult());
    }
}