package database;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.bank.AccountRepository;
import repository.bank.AccountRepositoryMySQL;
import repository.bank.ClientRepository;
import repository.bank.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.bank.AccountManagementService;
import service.bank.AccountManagementServiceMySQL;
import service.bank.ClientManagementService;
import service.bank.ClientManagementServiceMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static database.Constants.Rights.RIGHTS;
import static database.Constants.Roles.ROLES;
import static database.Constants.Schemas.SCHEMAS;
import static database.Constants.getRolesRights;

public class Bootstrap {
    private static RightsRolesRepository rightsRolesRepository;
    private static UserRepository userRepository;
    private static ClientRepository clientRepository;
    private static AccountRepository accountRepository;

    public static void main(String[] args) throws SQLException {
        dropAll();

        bootstrapTables();

        bootstrapUserData();

        bootstrapSampleData();


    }

    private static void dropAll() throws SQLException {
        for (String schema : SCHEMAS) {
            System.out.println("Dropping all tables in schema: " + schema);

            Connection connection = new JDBConnectionWrapper(schema).getConnection();
            Statement statement = connection.createStatement();

            String[] dropStatements = {
                    "TRUNCATE `role_right`",
                    "DROP TABLE `role_right`",
                    "TRUNCATE `rights`",
                    "TRUNCATE `user_role`",
                    "DROP TABLE `user_role`",
                    "TRUNCATE `role`",
                    "DROP TABLE role",
                    "TRUNCATE `transaction`",
                    "DROP TABLE transaction",
                    "TRUNCATE `bill`",
                    "DROP TABLE `bill`",
                    "TRUNCATE `account`",
                    "DROP TABLE `account`",
                    "TRUNCATE `client`",
                    "DROP TABLE `rights`",
                    "DROP TABLE  `client`, `user`;"

            };

            Arrays.stream(dropStatements).forEach(dropStatement -> {
                try {
                    statement.execute(dropStatement);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }

        System.out.println("Done table bootstrap");
    }

    private static void bootstrapTables() throws SQLException {
        SQLTableCreationFactory sqlTableCreationFactory = new SQLTableCreationFactory();

        for (String schema : SCHEMAS) {
            System.out.println("Bootstrapping " + schema + " schema");


            JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
            Connection connection = connectionWrapper.getConnection();

            Statement statement = connection.createStatement();

            for (String table : Constants.Tables.ORDERED_TABLES_FOR_CREATION) {
                String createTableSQL = sqlTableCreationFactory.getCreateSQLForTable(table);
                statement.execute(createTableSQL);
            }
        }

        System.out.println("Done table bootstrap");
    }

    private static void bootstrapUserData() throws SQLException {
        for (String schema : SCHEMAS) {
            System.out.println("Bootstrapping user data for " + schema);

            JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
            rightsRolesRepository = new RightsRolesRepositoryMySQL(connectionWrapper.getConnection());
            userRepository = new UserRepositoryMySQL(connectionWrapper.getConnection(), rightsRolesRepository);

            bootstrapRoles();
            bootstrapRights();
            bootstrapRoleRight();
            //bootstrapUserRoles();
        }
    }

    private static void bootstrapRoles() throws SQLException {
        for (String role : ROLES) {
            rightsRolesRepository.addRole(role);
        }
    }

    private static void bootstrapRights() throws SQLException {
        for (String right : RIGHTS) {
            rightsRolesRepository.addRight(right);
        }
    }

    private static void bootstrapRoleRight() throws SQLException {
        Map<String, List<String>> rolesRights = getRolesRights();

        for (String role : rolesRights.keySet()) {
            Long roleId = rightsRolesRepository.findRoleByTitle(role).getId();

            for (String right : rolesRights.get(role)) {
                Long rightId = rightsRolesRepository.findRightByTitle(right).getId();

                rightsRolesRepository.addRoleRight(roleId, rightId);
            }
        }
    }

    private static void bootstrapUserRoles() throws SQLException {

    }

    private static void bootstrapSampleData(){
        for (String schema : SCHEMAS) {

            JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
            rightsRolesRepository = new RightsRolesRepositoryMySQL(connectionWrapper.getConnection());
            userRepository = new UserRepositoryMySQL(connectionWrapper.getConnection(), rightsRolesRepository);
            clientRepository = new ClientRepositoryMySQL(connectionWrapper.getConnection());
            accountRepository = new AccountRepositoryMySQL(connectionWrapper.getConnection(), clientRepository);
            AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);

            Notification<Boolean> notification = authenticationService.register("admin@UT.com", "LongPassword!1", Constants.Roles.ADMINISTRATOR);
            if (notification.hasErrors()) {
                System.out.println(notification.getFormattedErrors());
            } else {
                System.out.println("Administrator registered");
            }
            ClientManagementService clientManagementService = new ClientManagementServiceMySQL(clientRepository);
            notification = clientManagementService.registerClient("Ionescu", "Cluj", "12345", "CJ123");
            if (notification.hasErrors()) {
                System.out.println(notification.getFormattedErrors());
            } else {
                System.out.println("Client Ionescu registered");
            }

            notification = clientManagementService.registerClient("Popescu", "Alba", "23456", "AB234");
            if (notification.hasErrors()) {
                System.out.println(notification.getFormattedErrors());
            } else {
                System.out.println("Client Popescu registered");
            }

            AccountManagementService accountManagementService = new AccountManagementServiceMySQL(accountRepository, clientRepository);


            notification = accountManagementService.createAccount("Ionescu", Constants.AccountTypes.CREDIT, 1000, new Date(System.currentTimeMillis()));
            if (notification.hasErrors()) {
                System.out.println(notification.getFormattedErrors());
            } else {
                System.out.println("Account for Ionescu created");
            }
            notification = accountManagementService.createAccount("Popescu", Constants.AccountTypes.DEBIT, 10000, new Date(System.currentTimeMillis()));
            if (notification.hasErrors()) {
                System.out.println(notification.getFormattedErrors());
            } else {
                System.out.println("Account for Popescu created");
            }
        }
    }

}
