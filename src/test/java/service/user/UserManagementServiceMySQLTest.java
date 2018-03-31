package service.user;

import database.Constants;
import database.DBConnectionFactory;
import model.Role;
import model.builder.UserBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserManagementServiceMySQLTest {
    private static UserManagementService userManagementService;
    private static UserRepository userRepository;
    private static RightsRolesRepository rightsRolesRepository;

    @Before
    public void setUp() throws Exception {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        userManagementService = new UserManagementServiceMySQL(userRepository,rightsRolesRepository);
        List<Role> roles = new ArrayList<>();
        roles.add(rightsRolesRepository.findRoleById(1L));
        userRepository.save(new UserBuilder()
                .setUsername("name@smt.com")
                .setPassword("pass")
                .setRoles(roles)
                .build());
    }

    @After
    public void tearDown() throws Exception {
        userManagementService.deleteUser("name@smt.com");
    }

    @Test
    public void updateUser() {
        List<String> roles = new ArrayList<>();
        roles.add(rightsRolesRepository.findRoleById(2L).getRole());
        Assert.assertTrue(userManagementService.updateUser("name@smt.com","pass",roles)
            .getResult());
    }

    @Test
    public void getUser() {
        Assert.assertNotNull(userManagementService.getUser("name@smt.com"));
    }

    @Test
    public void deleteUser() {
        Assert.assertTrue(userManagementService.deleteUser("name@smt.com"));
    }

    @Test
    public void getAllUsers() {
        Assert.assertTrue(userManagementService.getAllUsers().size()>0);
    }
}