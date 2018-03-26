
import database.Constants;
import database.DBConnectionFactory;
import repository.bank.AccountRepository;
import repository.bank.AccountRepositoryMySQL;
import repository.bank.ClientRepository;
import repository.bank.ClientRepositoryMySQL;
import repository.bill.BillRepository;
import repository.bill.BillRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.transaction.TransactionRepository;
import repository.transaction.TransactionRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.bank.*;
import service.bill.BillPaymentService;
import service.bill.BillPaymentServiceMySQL;
import service.transaction.TransactionService;
import service.transaction.TransactionServiceMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import service.user.UserManagementService;
import service.user.UserManagementServiceMySQL;

import java.sql.Connection;

public class ComponentFactory {
    private final AuthenticationService authenticationService;
    private final UserManagementService userManagementService;
    private final ClientManagementService clientManagementService;
    private final AccountTransferService accountTransferService;
    private final AccountManagementService accountManagementService;
    private final BillPaymentService billPaymentService;
    private final TransactionService transactionService;

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final ClientRepository clientRepository;
    private final BillRepository billRepository;
    private final TransactionRepository transactionRepository;

    private static ComponentFactory instance;

    private ComponentFactory(){
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection,rightsRolesRepository);
        clientRepository = new ClientRepositoryMySQL(connection);
        billRepository = new BillRepositoryMySQL(connection,userRepository);
        transactionRepository = new TransactionRepositoryMySQL(connection,userRepository);
        accountRepository = new AccountRepositoryMySQL(connection,clientRepository);
        authenticationService = new AuthenticationServiceMySQL(userRepository,rightsRolesRepository);
        userManagementService = new UserManagementServiceMySQL(userRepository,rightsRolesRepository);
        clientManagementService = new ClientManagementServiceMySQL(clientRepository);
        accountManagementService = new AccountManagementServiceMySQL(accountRepository, clientRepository);
        accountTransferService = new AccountTransferServiceMySQL(accountRepository);
        billPaymentService = new BillPaymentServiceMySQL(billRepository);
        transactionService = new TransactionServiceMySQL(transactionRepository);
    }

    public static ComponentFactory getInstance(){
        if (instance == null){
            instance = new ComponentFactory();
        }
        return instance;
    }


    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public UserManagementService getUserManagementService() {
        return userManagementService;
    }

    public ClientManagementService getClientManagementService() {
        return clientManagementService;
    }

    public AccountTransferService getAccountTransferService() {
        return accountTransferService;
    }

    public AccountManagementService getAccountManagementService() {
        return accountManagementService;
    }

    public BillPaymentService getBillPaymentService() {
        return billPaymentService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }



}
