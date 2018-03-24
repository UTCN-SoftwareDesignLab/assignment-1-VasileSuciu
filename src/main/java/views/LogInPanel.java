package views;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class LogInPanel extends JPanel {
	private JTextField userNameField;
	private JPasswordField passwordField;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JButton btnLogIn;

	/**
	 * Create the panel.
	 */
	public LogInPanel() {
		setLayout(null);
		
		userNameField = new JTextField();
		userNameField.setBounds(163, 104, 116, 22);
		add(userNameField);
		userNameField.setColumns(10);
		
		lblUsername = new JLabel("Username");
		lblUsername.setBounds(176, 75, 108, 16);
		add(lblUsername);
		
		lblPassword = new JLabel("Password");
		lblPassword.setBounds(173, 139, 86, 16);
		add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(163, 184, 116, 22);
		add(passwordField);
		
		btnLogIn = new JButton("Log In");
		btnLogIn.setBounds(163, 219, 97, 25);
		add(btnLogIn);

	}
	
	public void setBtnLogInActionListener(ActionListener action) {
		btnLogIn.addActionListener(action);
	}
	public String getPassword() {
		return new String(passwordField.getPassword());
	}
	public String getUserName() {
		return userNameField.getText();
	}
}
