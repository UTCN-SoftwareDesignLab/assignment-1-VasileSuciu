package views;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class AdministratorPanel extends JPanel {
	private JTextField userTextField;
	private JPasswordField passwordField;
	private JButton btnRegisterUser;
	private JLabel lblRoles_1;
	private JButton btnGenerateReport;
	private JLabel lblRoles;
	private JLabel lblUsers;
	private JButton btnRemoveRole;
	private JLabel lblReport;
	private JTextArea reportTextArea;
	private JButton btnAddRole;
	private JTextArea rolesTextArea;
	private JButton btnRemoveUser;
	private JButton btnSwitchView;
	private JComboBox<String> userComboBox;
	private JButton btnUpdateUser;
	private JButton btnLogOut;
	private JComboBox<String> roleComboBox;
	private JLabel lblUser;
	private JLabel lblPassword;

	/**
	 * Create the panel.
	 */
	public AdministratorPanel() {
		setLayout(null);

		initialize();

	}



	public void setBtnRegisterUser(ActionListener action) {
		btnRegisterUser.addActionListener(action);
	}
	public String getUserText() {
		return userTextField.getText();
	}
	
	public void setBtnGenerateReport(ActionListener action) {
		btnGenerateReport.addActionListener(action);
	}
	
	public String getPasswordField() {
		return new String(passwordField.getPassword());
	}
	public void setBtnRemoveRole(ActionListener action) {
		btnRemoveRole.addActionListener(action);
	}
	
	public void setReportTextArea(String report) {
		reportTextArea.setText(report);
	}
	
	public void setUserText(String user){
		userTextField.setText(user);
	}
	public void setBtnAddRole(ActionListener action) {
		btnAddRole.addActionListener(action);
	}
	public void setRolesTextArea(String roles) {
		rolesTextArea.setText(roles);
	}
	public void setBtnRemoveUser(ActionListener action) {
		btnRemoveUser.addActionListener(action);
	}
	public void setBtnSwitchView(ActionListener action) {
		btnSwitchView.addActionListener(action);
	}
	public void enableSwichView(boolean bool) {btnSwitchView.setEnabled(bool);}
	public String getUserComboBoxSelectedItem() {
		return (String)userComboBox.getSelectedItem();
	}
	public void setBtnUpdateUser(ActionListener action) {
		btnUpdateUser.addActionListener(action);
	}
	public void setBtnLogOut(ActionListener action) {
		btnLogOut.addActionListener(action);;
	}
	public String getRoleComboBoxSelectedItem() {
		return (String)roleComboBox.getSelectedItem();
	}
	
	public void setRoleComboBox(List<String> roles){
		roles.forEach(roleComboBox::addItem);
	}
	
	public void setUserComboBox(List<String> users){
		users.forEach(userComboBox::addItem);
	}
	
	public void setPasswordText(String password){
		passwordField.setText(password);
	}

	private void initialize() {
		btnLogOut = new JButton("Log Out");
		btnLogOut.setBounds(618, 24, 97, 25);
		add(btnLogOut);

		btnSwitchView = new JButton("Switch view");
		btnSwitchView.setBounds(474, 24, 120, 25);
		add(btnSwitchView);

		reportTextArea = new JTextArea();
		reportTextArea.setBounds(46, 366, 623, 224);
		add(reportTextArea);

		lblReport = new JLabel("Report");
		lblReport.setBounds(46, 337, 56, 16);
		add(lblReport);

		btnGenerateReport = new JButton("Generate Report");
		btnGenerateReport.setBounds(34, 309, 156, 25);
		add(btnGenerateReport);

		userComboBox = new JComboBox();
		userComboBox.setBounds(34, 116, 126, 22);
		add(userComboBox);

		lblUsers = new JLabel("Users");
		lblUsers.setBounds(34, 87, 56, 16);
		add(lblUsers);

		lblUser = new JLabel("User");
		lblUser.setBounds(227, 119, 56, 16);
		add(lblUser);

		lblPassword = new JLabel("Password");
		lblPassword.setBounds(227, 171, 56, 16);
		add(lblPassword);

		lblRoles = new JLabel("Roles");
		lblRoles.setBounds(227, 210, 56, 16);
		add(lblRoles);

		rolesTextArea = new JTextArea();
		rolesTextArea.setBounds(336, 207, 216, 87);
		add(rolesTextArea);

		userTextField = new JTextField();
		userTextField.setBounds(336, 116, 116, 22);
		add(userTextField);
		userTextField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(336, 168, 116, 22);
		add(passwordField);

		roleComboBox = new JComboBox();
		roleComboBox.setBounds(564, 116, 139, 22);
		add(roleComboBox);

		lblRoles_1 = new JLabel("Roles");
		lblRoles_1.setBounds(491, 119, 56, 16);
		add(lblRoles_1);

		btnRegisterUser = new JButton("Register user");
		btnRegisterUser.setBounds(35, 167, 125, 25);
		add(btnRegisterUser);

		btnUpdateUser = new JButton("Update user");
		btnUpdateUser.setBounds(34, 206, 126, 25);
		add(btnUpdateUser);

		btnRemoveUser = new JButton("Remove user");
		btnRemoveUser.setBounds(34, 257, 126, 25);
		add(btnRemoveUser);

		btnAddRole = new JButton("Add role");
		btnAddRole.setBounds(598, 167, 105, 25);
		add(btnAddRole);

		btnRemoveRole = new JButton("Remove role");
		btnRemoveRole.setBounds(598, 210, 105, 25);
		add(btnRemoveRole);
	}
}