package controllers;

import database.Constants;
import model.User;
import model.validation.Notification;
import service.transaction.TransactionService;
import service.user.AuthenticationService;
import service.user.UserManagementService;
import views.AdministratorPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AdministratorController {
    private User user;
    private ControllerInterface controllerInterface;
    private UserManagementService userManagementService;
    private TransactionService transactionService;
    private AdministratorPanel administratorPanel;
    private AuthenticationService authenticationService;

    public AdministratorController(AdministratorPanel administratorPanel,
                                   AuthenticationService authenticationService,
                                   UserManagementService userManagementService, TransactionService transactionService){
        this.administratorPanel =administratorPanel;
        this.authenticationService = authenticationService;
        this.transactionService = transactionService;
        this.userManagementService = userManagementService;
        this.user = null;
        setListeners();
        initializeView();
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setControllerInterface(ControllerInterface controllerInterface){
        this.controllerInterface = controllerInterface;
    }

    public JPanel getPanel(){
        return this.administratorPanel;
    }

    private void initializeView(){
        administratorPanel.setRoleComboBox(Arrays.asList(Constants.Roles.ROLES));
        administratorPanel.setUserComboBox(userManagementService.getAllUsers().stream().map(s->s.getUsername()).collect(Collectors.toList()));
        administratorPanel.enableSwichView(true);
        administratorPanel.setRoleComboBoxSelectedIndex(0);
        administratorPanel.setUsersComboBoxSelectedIndex(0);

    }

    private void setListeners(){
        administratorPanel.setBtnSwitchView(new SwitchButtonListener());
        administratorPanel.setBtnLogOut(new LogOutButtonListener());
        administratorPanel.setBtnRegisterUser(new RegisterUser());
        administratorPanel.setBtnUpdateUser(new UpdateUser());
        administratorPanel.setBtnRemoveUser(new RemoveUser());
        administratorPanel.setBtnGenerateReport(new GenerateReport());
        administratorPanel.setBtnAddRole(new AddRole());
        administratorPanel.setBtnRemoveRole(new RemoveRole());
        administratorPanel.setUserComboBoxItemListener(new UsersComboBoxItemListener());
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
            controllerInterface.gotoEmployeePanel();
        }
    }

    private class RegisterUser implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> notification = authenticationService.register(administratorPanel.getUserText(),
                    administratorPanel.getPasswordField());
            if (notification != null){
                if (notification.hasErrors()){
                    JOptionPane.showMessageDialog(controllerInterface.getContentPane(), notification.getFormattedErrors());
                }
                else{
                    transactionService.recordTransaction(user, Constants.TransactionType.USER_CREATION, new Date(System.currentTimeMillis()));
                    JOptionPane.showMessageDialog(controllerInterface.getContentPane(),"Registration successful!");
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
                    JOptionPane.showMessageDialog(controllerInterface.getContentPane(), notification.getFormattedErrors());
                }
                else{
                    transactionService.recordTransaction(user,Constants.TransactionType.USER_UPDATE, new Date(System.currentTimeMillis()));
                    JOptionPane.showMessageDialog(controllerInterface.getContentPane(),"Update successful!");
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
                JOptionPane.showMessageDialog(controllerInterface.getContentPane(), "User deleted");
            }
            else {
                JOptionPane.showMessageDialog(controllerInterface.getContentPane(),"Deletion not successful");
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

    private class UsersComboBoxItemListener implements ItemListener {
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
