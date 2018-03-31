package controllers;

import model.User;

import javax.swing.*;

public interface ControllerInterface {

    void gotoLogin();

    void gotoAdministratorPanel();

    void gotoEmployeePanel();

    void setUser(User user);

    JPanel getContentPane();
}
