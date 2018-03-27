package controllers;

import database.Constants;
import model.Account;
import model.Client;
import model.User;
import model.validation.Notification;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import repository.user.AuthenticationException;
import service.bank.AccountManagementService;
import service.bank.AccountTransferService;
import service.bank.ClientManagementService;
import service.bill.BillPaymentService;
import service.transaction.TransactionService;
import service.user.AuthenticationService;
import service.user.UserManagementService;
import views.AdministratorPanel;
import views.EmployeePanel;
import views.LogInPanel;
import views.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {

    private User user;
    private int currentView;

    private AccountManagementService accountManagementService;
    private AccountTransferService accountTransferService;
    private ClientManagementService clientManagementService;
    private BillPaymentService billPaymentService;
    private TransactionService transactionService;
    private AuthenticationService authenticationService;
    private UserManagementService userManagementService;

    private MainFrame mainFrame;
    private LogInPanel logInPanel;
    private EmployeePanel employeePanel;
    private AdministratorPanel administratorPanel;

    public Controller(AccountManagementService accountManagementService, AccountTransferService accountTransferService,
                      ClientManagementService clientManagementService, BillPaymentService billPaymentService,
                      TransactionService transactionService, AuthenticationService authenticationService, UserManagementService userManagementService,
                      MainFrame mainFrame, LogInPanel logInPanel, EmployeePanel employeePanel, AdministratorPanel administratorPanel) {
        this.accountManagementService = accountManagementService;
        this.accountTransferService = accountTransferService;
        this.clientManagementService = clientManagementService;
        this.billPaymentService = billPaymentService;
        this.transactionService = transactionService;
        this.authenticationService = authenticationService;
        this.userManagementService = userManagementService;
        this.mainFrame = mainFrame;
        this.logInPanel = logInPanel;
        this.employeePanel = employeePanel;
        this.administratorPanel = administratorPanel;
        mainFrame.changeContent(logInPanel);
        currentView = -1;
        setListeners();
    }

    private void setListeners(){
        logInPanel.setBtnLogInActionListener(new LoginButtonListener());
        employeePanel.setBtnLogOut(new LogOutButtonListener());
        administratorPanel.setBtnLogOut(new LogOutButtonListener());
        employeePanel.setBtnSwitchView(new SwitchButtonListener());
        administratorPanel.setBtnSwitchView(new SwitchButtonListener());
        employeePanel.setBtnTransfer(new TransferButtonListener());
        employeePanel.setBtnPayBill(new PayBillListener());
        employeePanel.setBtnAddClient(new AddClient());
        employeePanel.setBtnUpdateClient(new UpdateClient());
        employeePanel.setBtnRemoveClient(new DeleteClient());
        employeePanel.setBtnCreateAccount(new CreateAccount());
        employeePanel.setBtnUpdateAccount(new UpdateAccount());
        employeePanel.setBtnRemoveAccount(new DeleteAccount());
        employeePanel.setAccountFromItemListener(new AccountFromItemListener());
        employeePanel.setClientFromItemListener(new ClientFromItemListener());
        employeePanel.setClientToItemListener(new ClientToItemListener());
        administratorPanel.setBtnRegisterUser(new RegisterUser());
        administratorPanel.setBtnUpdateUser(new UpdateUser());
        administratorPanel.setBtnRemoveUser(new RemoveUser());
        administratorPanel.setBtnGenerateReport(new GenerateReport());
        administratorPanel.setBtnAddRole(new AddRole());
        administratorPanel.setBtnRemoveRole(new RemoveRole());
        administratorPanel.setUserComboBoxItemListener(new UsersComboBoxItemListener());

    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = logInPanel.getUserName();
            String password = logInPanel.getPassword();

            Notification<User> loginNotification = null;
            try {
                loginNotification = authenticationService.login(username, password);
            } catch (AuthenticationException e1) {
                e1.printStackTrace();
            }

            if (loginNotification != null) {
                if (loginNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(mainFrame.getContentPane(), loginNotification.getFormattedErrors());
                } else {
                    JOptionPane.showMessageDialog(mainFrame.getContentPane(), "Login successful!");
                    user = loginNotification.getResult();
                    employeePanel.setBtnSwitchViewEnable(false);
                    employeePanel.setClientFromComboBox(clientManagementService.getAllClientsName());
                    employeePanel.setClientToComboBox(clientManagementService.getAllClientsName());
                    employeePanel.setClientFromSelectedIndex(0);
                    employeePanel.setClientToSelectedIndex(0);
                    employeePanel.setAccoutFromComboBox(accountManagementService
                                                    .getAccountsByClient(employeePanel.getClientFromComboBox())
                                                    .stream().map(s->s.getId()).collect(Collectors.toList()));
                    employeePanel.setAccoutToComboBox(accountManagementService
                            .getAccountsByClient(employeePanel.getClientToComboBox())
                            .stream().map(s->s.getId()).collect(Collectors.toList()));
                    employeePanel.setAccountTypeComboBox(Arrays.asList(Constants.AccountTypes.TYPES));
                    employeePanel.setAccountTypeSelectedIndex(0);
                    employeePanel.setAccountFromSelectedIndex(0);
                    employeePanel.setAccountToSelectedIndex(0);
                    if (user.getRoles().stream().map(s->s.getRole()).collect(Collectors.toList()).contains(Constants.Roles.ADMINISTRATOR)){
                        administratorPanel.setRoleComboBox(Arrays.asList(Constants.Roles.ROLES));
                        administratorPanel.setUserComboBox(userManagementService.getAllUsers().stream().map(s->s.getUsername()).collect(Collectors.toList()));
                        administratorPanel.enableSwichView(true);
                        administratorPanel.setRoleComboBoxSelectedIndex(0);
                        administratorPanel.setUsersComboBoxSelectedIndex(0);
                        employeePanel.setBtnSwitchViewEnable(true);
                        mainFrame.changeContent(administratorPanel);
                        currentView = 0;
                    }
                    else {
                        currentView = 1;
                        mainFrame.changeContent(employeePanel);
                    }

                }
            }
        }
    }

    private class LogOutButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            user = null;
            mainFrame.changeContent(logInPanel);
        }
    }

    private class SwitchButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentView == 0){
                currentView = 1;
                mainFrame.changeContent(employeePanel);
            }
            else {
                currentView = 0;
                mainFrame.changeContent(administratorPanel);
            }
        }
    }

    private class TransferButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> notification = accountTransferService.moneyTransfer(employeePanel.getAccountFromComboBox(),
                    employeePanel.getAccountToComboBox(), Double.parseDouble(employeePanel.getAmountTextField().trim()));
            if (notification!= null){
                if (notification.hasErrors()) {
                    JOptionPane.showMessageDialog(mainFrame.getContentPane(), notification.getFormattedErrors());
                } else {
                    transactionService.recordTransaction(user,Constants.TransactionType.ACCOUNTS_TRANSFER, new Date(System.currentTimeMillis()));
                    JOptionPane.showMessageDialog(mainFrame.getContentPane(), "Transfer successful!");
                }
            }
        }
    }

    private class PayBillListener implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> notification = billPaymentService.payBill(user,employeePanel.getCompany(),Double.parseDouble(employeePanel.getTotal().trim()));
            if (notification!= null){
                if (notification.hasErrors()) {
                    JOptionPane.showMessageDialog(mainFrame.getContentPane(), notification.getFormattedErrors());
                } else {
                    transactionService.recordTransaction(user,Constants.TransactionType.PAYING_BILL, new Date(System.currentTimeMillis()));
                    JOptionPane.showMessageDialog(mainFrame.getContentPane(), "Bill paid successfully!");
                }
            }
        }
    }

    private class AddClient implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> notification = clientManagementService.registerClient(employeePanel.getNameField().trim(),
                    employeePanel.getAddress().trim(),
                    employeePanel.getPersonalNumericalCode().trim(),
                    employeePanel.getIdCardNumber().trim());
            if (notification!= null){
                if (notification.hasErrors()) {
                    JOptionPane.showMessageDialog(mainFrame.getContentPane(), notification.getFormattedErrors());
                } else {
                    transactionService.recordTransaction(user,Constants.TransactionType.CLIENT_CREATION, new Date(System.currentTimeMillis()));
                    JOptionPane.showMessageDialog(mainFrame.getContentPane(), "Client registered successfully!");
                }
            }
        }
    }

    private class UpdateClient implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> notification = clientManagementService.updateClient(employeePanel.getNameField().trim(),
                    employeePanel.getAddress().trim(),
                    employeePanel.getPersonalNumericalCode().trim(),
                    employeePanel.getIdCardNumber().trim());
            if (notification!= null){
                if (notification.hasErrors()) {
                    JOptionPane.showMessageDialog(mainFrame.getContentPane(), notification.getFormattedErrors());
                } else {
                    transactionService.recordTransaction(user,Constants.TransactionType.CLIENT_UPDATE, new Date(System.currentTimeMillis()));
                    JOptionPane.showMessageDialog(mainFrame.getContentPane(), "Client updated successfully!");
                }
            }
        }
    }

    private class DeleteClient implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean clientDeleted = clientManagementService.deleteClient(employeePanel.getNameField().trim());
            if (!clientDeleted) {
                JOptionPane.showMessageDialog(mainFrame.getContentPane(), "Client not deleted");
            }
            else {
                transactionService.recordTransaction(user,Constants.TransactionType.CLIENT_REMOVAL, new Date(System.currentTimeMillis()));
                JOptionPane.showMessageDialog(mainFrame.getContentPane(), "Client deleted successfully!");
            }

        }
    }

    private class CreateAccount implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> notification = accountManagementService.createAccount(employeePanel.getClientFromComboBox(),
                    employeePanel.getAccountType(),
                    Double.parseDouble(employeePanel.getBalance().trim()),
                    new Date(System.currentTimeMillis()));
            if (notification!= null){
                if (notification.hasErrors()) {
                    JOptionPane.showMessageDialog(mainFrame.getContentPane(), notification.getFormattedErrors());
                } else {
                    transactionService.recordTransaction(user,Constants.TransactionType.ACCOUNT_CREATION, new Date(System.currentTimeMillis()));
                    JOptionPane.showMessageDialog(mainFrame.getContentPane(), "Account created successfully!");
                }
            }
        }
    }

    private class UpdateAccount implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> notification = accountManagementService.updateAccount(employeePanel.getAccountFromComboBox(),
                    employeePanel.getAccountType(),
                    Double.parseDouble(employeePanel.getBalance().trim()));
            if (notification!= null){
                if (notification.hasErrors()) {
                    JOptionPane.showMessageDialog(mainFrame.getContentPane(), notification.getFormattedErrors());
                } else {
                    transactionService.recordTransaction(user,Constants.TransactionType.ACCOUNT_UPDATE, new Date(System.currentTimeMillis()));
                    JOptionPane.showMessageDialog(mainFrame.getContentPane(), "Account updated successfully!");
                }
            }
        }
    }

    private class DeleteAccount implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean accountDeleted = accountManagementService.removeAccount(employeePanel.getAccountFromComboBox());
            if (!accountDeleted) {
                JOptionPane.showMessageDialog(mainFrame.getContentPane(), "Account not deleted");
            }
            else {
                transactionService.recordTransaction(user,Constants.TransactionType.ACCOUNT_REMOVAL, new Date(System.currentTimeMillis()));
                JOptionPane.showMessageDialog(mainFrame.getContentPane(), "Account deleted successfully!");
            }

        }
    }

    private class ClientFromItemListener implements ItemListener{

        @Override
        public void itemStateChanged(ItemEvent arg0) {
            employeePanel.setAccoutFromComboBox(accountManagementService
                    .getAccountsByClient(employeePanel.getClientFromComboBox())
                    .stream().map(s->s.getId()).collect(Collectors.toList()));
            Client client = clientManagementService.getClient(employeePanel.getClientFromComboBox());
            employeePanel.setNameField(client.getName());
            employeePanel.setAddress(client.getAddress());
            employeePanel.setPersonalNumericalCode(client.getPersonalNumericalCode());
            employeePanel.setIdCardNumber(client.getIdCardNumber());
            employeePanel.setAccountFromSelectedIndex(0);
        }
    }

    private class ClientToItemListener implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent arg0) {
            employeePanel.setAccoutToComboBox(accountManagementService
                    .getAccountsByClient(employeePanel.getClientToComboBox())
                    .stream().map(s->s.getId()).collect(Collectors.toList()));
            employeePanel.setAccountToSelectedIndex(0);
        }
    }

    private class AccountFromItemListener implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent e) {
            Long accountID = employeePanel.getAccountFromComboBox();
            Account account= accountManagementService.getAccountById(accountID);
            employeePanel.setAccountID(accountID);
            employeePanel.setCreationDate(account.getCreationDate().toString());
            employeePanel.setBalance(String.valueOf(account.getBalance()));
            if (account.getType().equals(Constants.AccountTypes.CREDIT)) {
                employeePanel.setAccountTypeSelectedIndex(0);
            }
            else{
                employeePanel.setAccountTypeSelectedIndex(1);
            }
        }
    }

    private class RegisterUser implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> notification = authenticationService.register(administratorPanel.getUserText(),
                    administratorPanel.getPasswordField());
            if (notification != null){
                if (notification.hasErrors()){
                    JOptionPane.showMessageDialog(mainFrame.getContentPane(), notification.getFormattedErrors());
                }
                else{
                    transactionService.recordTransaction(user,Constants.TransactionType.USER_CREATION, new Date(System.currentTimeMillis()));
                    JOptionPane.showMessageDialog(mainFrame.getContentPane(),"Registration successful!");
                }
            }
        }
    }

    private class UpdateUser implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            List<String> roles = Arrays.stream(Constants.Roles.ROLES).filter(administratorPanel.getRolesText()::contains).collect(Collectors.toList());
            Notification<Boolean> notification= userManagementService.updateUser(administratorPanel.getUserText(), administratorPanel.getPasswordField(), roles);
            if (notification != null){
                if (notification.hasErrors()){
                    JOptionPane.showMessageDialog(mainFrame.getContentPane(), notification.getFormattedErrors());
                }
                else{
                    transactionService.recordTransaction(user,Constants.TransactionType.USER_UPDATE, new Date(System.currentTimeMillis()));
                    JOptionPane.showMessageDialog(mainFrame.getContentPane(),"Update successful!");
                }
            }
        }
    }

    private class RemoveUser implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean userDeleted = userManagementService.deleteUser(administratorPanel.getUserComboBoxSelectedItem());
            if (userDeleted){
                transactionService.recordTransaction(user,Constants.TransactionType.USER_REMOVAL, new Date(System.currentTimeMillis()));
                JOptionPane.showMessageDialog(mainFrame.getContentPane(), "User deleted");
            }
            else {
                JOptionPane.showMessageDialog(mainFrame.getContentPane(),"Deletion not successful");
            }
        }
    }

    private class GenerateReport implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            administratorPanel.setReportTextArea(transactionService.getReportForUser(administratorPanel.getUserComboBoxSelectedItem()).toString());
        }
    }

    private  class AddRole implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String role = administratorPanel.getRoleComboBoxSelectedItem();
            if (!administratorPanel.getRolesText().contains(role)){
                administratorPanel.setRolesTextArea(administratorPanel.getRolesText()+" "+role);
            }
        }
    }

    private class RemoveRole implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String role = administratorPanel.getRoleComboBoxSelectedItem();
            if (administratorPanel.getRolesText().contains(role)){
                administratorPanel.setRolesTextArea(administratorPanel.getRolesText().replaceAll(role,""));
            }
        }
    }

    private class UsersComboBoxItemListener implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent e) {
            User newUser = userManagementService.getUser(administratorPanel.getUserComboBoxSelectedItem());
            administratorPanel.setUserText(newUser.getUsername());
            administratorPanel.setPasswordText(newUser.getPassword());
            administratorPanel.setRolesTextArea(newUser.getRoles().stream()
                .map(s->s.getRole()+" ").reduce("", String::concat));
        }
    }
}
