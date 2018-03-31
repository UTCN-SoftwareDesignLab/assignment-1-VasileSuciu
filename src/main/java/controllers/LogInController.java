package controllers;

import database.Constants;
import model.User;
import model.validation.Notification;
import repository.user.AuthenticationException;
import service.user.AuthenticationService;
import views.LogInPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.Collectors;

public class LogInController {
    private LogInPanel logInPanel;
    private ControllerInterface controllerInterface;
    private AuthenticationService authenticationService;

    public LogInController(LogInPanel logInPanel, AuthenticationService authenticationService) {
        this.logInPanel = logInPanel;
        this.authenticationService = authenticationService;
        logInPanel.setBtnLogInActionListener(new LoginButtonListener());
    }

    public void setControllerInterface(ControllerInterface controllerInterface){
        this.controllerInterface = controllerInterface;
    }

    public JPanel getPanel(){
        return this.logInPanel;
    };

    private class LoginButtonListener implements ActionListener{
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

            if (loginNotification != null){
                if (loginNotification.hasErrors()){
                    JOptionPane.showMessageDialog(controllerInterface.getContentPane(), loginNotification.getFormattedErrors());
                } else {
                    User user = loginNotification.getResult();
                    controllerInterface.setUser(user);
                    if (user.getRoles().stream().map(s->s.getRole()).collect(Collectors.toList()).contains(Constants.Roles.ADMINISTRATOR)){
                        controllerInterface.gotoAdministratorPanel();
                    } else {
                        controllerInterface.gotoEmployeePanel();
                    }
                }
            }
        }

    }


}
