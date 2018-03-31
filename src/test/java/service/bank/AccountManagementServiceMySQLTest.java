package service.bank;

import database.Constants;
import database.DBConnectionFactory;
import model.Account;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.bank.AccountRepository;
import repository.bank.AccountRepositoryMySQL;
import repository.bank.ClientRepository;
import repository.bank.ClientRepositoryMySQL;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;

public class AccountManagementServiceMySQLTest {
    private AccountManagementService accountManagementService;
    private ClientRepository clientRepository;
    private AccountRepository accountRepository;
    private Account account;
    private Account account2;

    @Before
    public void setUp() throws Exception {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        clientRepository = new ClientRepositoryMySQL(connection);
        accountRepository = new AccountRepositoryMySQL(connection,clientRepository);
        accountManagementService = new AccountManagementServiceMySQL(accountRepository,clientRepository);
        accountManagementService.createAccount("Ionescu", Constants.AccountTypes.CREDIT, 1000, new Date(System.currentTimeMillis()));
        accountManagementService.createAccount("Ionescu", Constants.AccountTypes.CREDIT, 1000, new Date(System.currentTimeMillis()));
        List<Account> accountList = accountManagementService.getAccountsByClient("Ionescu");
        account = accountList.get(accountList.size()-1);
        account2 = accountList.get(accountList.size()-2);
    }

    @After
    public void tearDown() throws Exception {
        List<Account> accounts = accountManagementService.getAccountsByClient("Ionescu");
        for (int i = 1; i<accounts.size(); i++){
            accountManagementService.removeAccount(accounts.get(i).getId());
        }
    }

    @Test
    public void createAccount() {
        Assert.assertTrue(accountManagementService.createAccount("Ionescu", Constants.AccountTypes.CREDIT, 2000, new Date(System.currentTimeMillis()))
        .getResult());
    }

    @Test
    public void updateAccount() {
        Assert.assertTrue(accountManagementService.updateAccount(account.getId(), account.getType(), account.getBalance() + 10)
                        .getResult());
    }

    @Test
    public void removeAccount() {
        Assert.assertTrue(accountManagementService.removeAccount(account2.getId()));
    }

    @Test
    public void getAccountById() {
        Assert.assertNotNull(accountManagementService.getAccountById(1L));
    }

    @Test
    public void getAccountsByClient() {
        Assert.assertTrue(accountManagementService.getAccountsByClient("Ionescu").size()>0);
    }
}