package controllers;

import model.User;
import views.LogInPanel;
import views.MainFrame;

import javax.swing.*;

public class MainController implements ControllerInterface{
    private User user;
    private MainFrame mainFrame;
    private EmployeeController employeeController;
    private LogInController logInController;
    private AdministratorController administratorController;

    public MainController(MainFrame mainFrame, EmployeeController employeeController, LogInController logInController, AdministratorController administratorController) {
        this.mainFrame = mainFrame;
        this.employeeController = employeeController;
        this.logInController = logInController;
        this.administratorController = administratorController;
        employeeController.setControllerInterface(this);
        logInController.setControllerInterface(this);
        administratorController.setControllerInterface(this);
        mainFrame.changeContent(logInController.getPanel());
    }

    @Override
    public void gotoLogin() {
        mainFrame.changeContent(logInController.getPanel());
    }

    @Override
    public void gotoAdministratorPanel() {
        mainFrame.changeContent(administratorController.getPanel());
    }

    @Override
    public void gotoEmployeePanel() {
        mainFrame.changeContent(employeeController.getPanel());
    }

    @Override
    public void setUser(User user) {
        this.user = user;
        administratorController.setUser(user);
        employeeController.setUser(user);
    }

    @Override
    public JPanel getContentPane() {
        return null;
    }
}
