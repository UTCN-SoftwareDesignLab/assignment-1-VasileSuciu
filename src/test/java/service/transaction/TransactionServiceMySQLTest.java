package service.transaction;

import database.Constants;
import database.DBConnectionFactory;
import model.Transaction;
import model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.security.RightsRolesRepositoryMySQL;
import repository.transaction.TransactionRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import javax.xml.transform.sax.SAXSource;
import java.sql.Connection;
import java.sql.Date;

import static org.junit.Assert.*;

public class TransactionServiceMySQLTest {
    private TransactionService transactionService;
    private UserRepository userRepository;
    private  User user;

    @Before
    public void setUp() throws Exception {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        userRepository = new UserRepositoryMySQL(connection, new RightsRolesRepositoryMySQL(connection));
        user = userRepository.findById(1L);
        transactionService =  new TransactionServiceMySQL(new TransactionRepositoryMySQL(connection, userRepository));

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void recordTransaction() {
        Assert.assertTrue(transactionService.recordTransaction(user, Constants.TransactionType.PAYING_BILL, new Date(System.currentTimeMillis())));
    }

    @Test
    public void getReportForUser() {
        Assert.assertNotNull(transactionService.getReportForUser(user.getUsername()));
    }
}