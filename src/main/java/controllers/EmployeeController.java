package controllers;

import database.Constants;
import model.Account;
import model.Client;
import model.User;
import model.validation.Notification;
import service.bank.AccountManagementService;
import service.bank.AccountTransferService;
import service.bank.ClientManagementService;
import service.bill.BillPaymentService;
import service.transaction.TransactionService;
import views.EmployeePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.util.Arrays;
import java.util.stream.Collectors;

public class EmployeeController {
    private User user;
    private ControllerInterface controllerInterface;
    private EmployeePanel employeePanel;
    private AccountManagementService accountManagementService;
    private AccountTransferService accountTransferService;

    public EmployeeController(EmployeePanel employeePanel,
                              AccountManagementService accountManagementService, AccountTransferService accountTransferService,
                              ClientManagementService clientManagementService, BillPaymentService billPaymentService, TransactionService transactionService) {
        this.employeePanel = employeePanel;
        this.accountManagementService = accountManagementService;
        this.accountTransferService = accountTransferService;
        this.clientManagementService = clientManagementService;
        this.billPaymentService = billPaymentService;
        this.transactionService = transactionService;
        this.user =null;
        setListeners();
        initializeView();
    }

    private ClientManagementService clientManagementService;
    private BillPaymentService billPaymentService;
    private TransactionService transactionService;

    public void setUser(User user){
        this.user = user;
        if (user.getRoles().stream().map(s->s.getRole()).collect(Collectors.toList()).contains(Constants.Roles.ADMINISTRATOR)){
            employeePanel.setBtnSwitchViewEnable(true);
        }
        else{
            employeePanel.setBtnSwitchViewEnable(false);
        }
    }

    public void setControllerInterface(ControllerInterface controllerInterface){
        this.controllerInterface = controllerInterface;
    }

    public JPanel getPanel(){
        return this.employeePanel;
    }

    private void initializeView(){
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
        employeePanel.setBtnSwitchViewEnable(false);
    }

    private void setListeners(){
        employeePanel.setBtnLogOut(new LogOutButtonListener());
        employeePanel.setBtnSwitchView(new SwitchButtonListener());
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
    }

    private class LogOutButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            user = null;
            controllerInterface.gotoLogin();
        }
    }

    private class SwitchButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            controllerInterface.gotoAdministratorPanel();
        }
    }

    private class TransferButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> notification = accountTransferService.moneyTransfer(employeePanel.getAccountFromComboBox(),
                    employeePanel.getAccountToComboBox(), Double.parseDouble(employeePanel.getAmountTextField().trim()));
            if (notification!= null){
                if (notification.hasErrors()) {
                    JOptionPane.showMessageDialog(controllerInterface.getContentPane(), notification.getFormattedErrors());
                } else {
                    transactionService.recordTransaction(user, Constants.TransactionType.ACCOUNTS_TRANSFER, new Date(System.currentTimeMillis()));
                    JOptionPane.showMessageDialog(controllerInterface.getContentPane(), "Transfer successful!");
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
                    JOptionPane.showMessageDialog(controllerInterface.getContentPane(), notification.getFormattedErrors());
                } else {
                    transactionService.recordTransaction(user,Constants.TransactionType.PAYING_BILL, new Date(System.currentTimeMillis()));
                    JOptionPane.showMessageDialog(controllerInterface.getContentPane(), "Bill paid successfully!");
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
                    JOptionPane.showMessageDialog(controllerInterface.getContentPane(), notification.getFormattedErrors());
                } else {
                    transactionService.recordTransaction(user,Constants.TransactionType.CLIENT_CREATION, new Date(System.currentTimeMillis()));
                    JOptionPane.showMessageDialog(controllerInterface.getContentPane(), "Client registered successfully!");
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
                    JOptionPane.showMessageDialog(controllerInterface.getContentPane(), notification.getFormattedErrors());
                } else {
                    transactionService.recordTransaction(user,Constants.TransactionType.CLIENT_UPDATE, new Date(System.currentTimeMillis()));
                    JOptionPane.showMessageDialog(controllerInterface.getContentPane(), "Client updated successfully!");
                }
            }
        }
    }

    private class DeleteClient implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean clientDeleted = clientManagementService.deleteClient(employeePanel.getNameField().trim());
            if (!clientDeleted) {
                JOptionPane.showMessageDialog(controllerInterface.getContentPane(), "Client not deleted");
            }
            else {
                transactionService.recordTransaction(user,Constants.TransactionType.CLIENT_REMOVAL, new Date(System.currentTimeMillis()));
                JOptionPane.showMessageDialog(controllerInterface.getContentPane(), "Client deleted successfully!");
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
                    JOptionPane.showMessageDialog(controllerInterface.getContentPane(), notification.getFormattedErrors());
                } else {
                    transactionService.recordTransaction(user,Constants.TransactionType.ACCOUNT_CREATION, new Date(System.currentTimeMillis()));
                    JOptionPane.showMessageDialog(controllerInterface.getContentPane(), "Account created successfully!");
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
                    JOptionPane.showMessageDialog(controllerInterface.getContentPane(), notification.getFormattedErrors());
                } else {
                    transactionService.recordTransaction(user,Constants.TransactionType.ACCOUNT_UPDATE, new Date(System.currentTimeMillis()));
                    JOptionPane.showMessageDialog(controllerInterface.getContentPane(), "Account updated successfully!");
                }
            }
        }
    }

    private class DeleteAccount implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean accountDeleted = accountManagementService.removeAccount(employeePanel.getAccountFromComboBox());
            if (!accountDeleted) {
                JOptionPane.showMessageDialog(controllerInterface.getContentPane(), "Account not deleted");
            }
            else {
                transactionService.recordTransaction(user,Constants.TransactionType.ACCOUNT_REMOVAL, new Date(System.currentTimeMillis()));
                JOptionPane.showMessageDialog(controllerInterface.getContentPane(), "Account deleted successfully!");
            }

        }
    }

    private class ClientFromItemListener implements ItemListener {

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

}
