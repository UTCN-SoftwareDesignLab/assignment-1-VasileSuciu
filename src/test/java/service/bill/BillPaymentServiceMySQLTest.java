package service.bill;

import database.DBConnectionFactory;
import model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.bill.BillRepositoryMySQL;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.sql.Connection;

import static org.junit.Assert.*;

public class BillPaymentServiceMySQLTest {
    private static BillPaymentService billPaymentService;
    private static User user;
    private static UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        userRepository = new UserRepositoryMySQL(connection, new RightsRolesRepositoryMySQL(connection));
        user = userRepository.findById(1L);
        billPaymentService  = new BillPaymentServiceMySQL(new BillRepositoryMySQL(connection,userRepository));

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void payBill() {
        Assert.assertTrue(billPaymentService.payBill(user, "Vodafone", 50).getResult());
    }
}