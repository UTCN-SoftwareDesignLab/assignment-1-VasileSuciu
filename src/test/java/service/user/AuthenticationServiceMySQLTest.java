package service.user;

import database.DBConnectionFactory;
import model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.AuthenticationException;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.sql.Connection;

import static org.junit.Assert.*;

public class AuthenticationServiceMySQLTest {
    private static AuthenticationService authenticationService;
    private static UserRepository userRepository;
    private static RightsRolesRepository rightsRolesRepository;

    @Before
    public void setUp() throws Exception {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection,rightsRolesRepository);
        authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);
    }

    @After
    public void tearDown() throws Exception {
        User user  = userRepository.findByUsername("Test@test.com");
        if (user != null){
            rightsRolesRepository.deleteRolesforUser(user.getId());
            userRepository.removeUser(user);
        }
    }

    @Test
    public void register() {
        Assert.assertTrue(authenticationService.register("Test@test.com","Pass2Test_").getResult());
    }


    @Test
    public void login() throws AuthenticationException{
        Assert.assertNotNull(authenticationService.login("admin@UT.com","LongPassword!1").getResult());
    }

    @Test
    public void logout() {
        authenticationService.logout(userRepository.findById(1L));
    }
}