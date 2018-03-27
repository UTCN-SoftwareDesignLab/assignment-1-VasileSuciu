package views;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.List;

public class EmployeePanel extends JPanel {
	private JTextField companyTextField;
	private JTextField amountTextField;
	private JTextField totalTextField;
	private JTextField name;
	private JTextField address;
	private JTextField personalNumericalCode;
	private JTextField idCardNumber;
	private JTextField accountID;
	private JTextField balance;
	private JTextField creationDate;
	private JComboBox<Long> accountFromComboBox;
	private JLabel lblName;
	private JLabel lblAccountfrom;
	private JButton btnUpdateAccount;
	private JLabel lblCompany;
	private JComboBox<String> clientFromComboBox;
	private JComboBox<String> clientToComboBox;
	private JLabel lblBill;
	private JButton btnPayBill;
	private JLabel lblBalance;
	private JLabel lblAccountTo;
	private JLabel lblAmmount;
	private JLabel lblAddress;
	private JButton btnUpdateClient;
	private JButton btnTransfer;
	private JLabel lblClientfrom;
	private JButton btnAddClient;
	private JButton btnSwitchView;
	private JComboBox<Long> accountToComboBox;
	private JLabel lblTotal;
	private JLabel lblClientTo;
	private JButton btnRemoveAccount;
	private JLabel lblCreationDate;
	private JLabel lblAccountId;
	private JLabel lblIdCardNumber;
	private JButton btnLogOut;
	private JLabel lblPersonalNumericalCode;
	private JComboBox<String> accountType;
	private JLabel lblType;
	private JButton btnRemoveClient;
	private JButton btnCreateAccount;

	/**
	 * Create the panel.
	 */
	public EmployeePanel() {
		setLayout(null);
		
		initialize();

	}

	
	public void setAccountID(Long id){accountID.setText(String.valueOf(id));}
	public Long getAccountFromComboBox() {
		return (Long)accountFromComboBox.getSelectedItem();
	}
	
	public void setBtnUpdateAccount(ActionListener action) {
		btnUpdateAccount.addActionListener(action);
	}
	
	public String getCompany() { return companyTextField.getText(); }
	public String getClientFromComboBox() {
		return (String)clientFromComboBox.getSelectedItem();
	}
	public String getCreationDate() {
		return creationDate.getText();
	}
	public void setCreationDate(String date){ creationDate.setText(date);}
	public String getClientToComboBox() {
		return (String)clientToComboBox.getSelectedItem();
	}
	
	public void setBtnPayBill(ActionListener action) {
		btnPayBill.addActionListener(action);
	}
	public String getNameField() {
		return name.getText();
	}
	public void setNameField(String clientName){ name.setText(clientName);}
	
	public String getBalance() {
		return balance.getText();
	}
	public void setBalance(String balance1) {balance.setText(balance1);}
	
	public void setBtnUpdateClient(ActionListener action) {
		btnUpdateClient.addActionListener(action);
	}
	
	public String getPersonalNumericalCode() {
		return personalNumericalCode.getText();
	}
	public void setPersonalNumericalCode(String pnc) {personalNumericalCode.setText(pnc);}

	public void setBtnTransfer(ActionListener action) {
		btnTransfer.addActionListener(action);
	}
	
	public void setBtnAddClient(ActionListener action) {
		btnAddClient.addActionListener(action);
	}
	public String getAmountTextField() {
		return amountTextField.getText();
	}
	public String getAddress() {
		return address.getText();
	}
	public void setAddress(String addrs){address.setText(addrs);}
	
	public void setBtnSwitchView(ActionListener action) {
		btnSwitchView.addActionListener(action);
	}
	
	public void setBtnSwitchViewEnable(boolean enable){
		btnSwitchView.setEnabled(enable);
	}
	
	public Long getAccountToComboBox() {
		return (Long)accountToComboBox.getSelectedItem();
	}
	
	public String getIdCardNumber() {
		return idCardNumber.getText();
	}
	public void setIdCardNumber(String id){idCardNumber.setText(id);}
	public void setBtnRemoveAccount(ActionListener action) {
		btnRemoveAccount.addActionListener(action);
	}
	
	public String getTotal() {
		return totalTextField.getText();
	}
	
	public void setBtnLogOut(ActionListener action) {
		btnLogOut.addActionListener(action);
	}
	
	public String getAccountType() {
		return (String)accountType.getSelectedItem();
	}
	
	public void setBtnRemoveClient(ActionListener action) {
		btnRemoveClient.addActionListener(action);
	}
	public void setBtnCreateAccount(ActionListener action) {
		btnCreateAccount.addActionListener(action);
	}
	public void setAccoutFromComboBox(List<Long> accounts){
		accountFromComboBox.setModel(new DefaultComboBoxModel<Long>());
		accounts.forEach(accountFromComboBox::addItem);
	}
	public void setAccoutToComboBox(List<Long> accounts){
		accountToComboBox.setModel(new DefaultComboBoxModel<Long>());
		accounts.forEach(accountToComboBox::addItem);
	}
	public void setClientFromComboBox(List<String> clients){
		clientFromComboBox.setModel(new DefaultComboBoxModel<String>());
		clients.forEach(clientFromComboBox::addItem);
	}
	public void setClientToComboBox(List<String> clients){
		clientToComboBox.setModel(new DefaultComboBoxModel<String>());
		clients.forEach(clientToComboBox::addItem);
	}
	public void setAccountTypeComboBox(List<String> types){
		types.forEach(accountType::addItem);
	}
	public void setAccountFromSelectedIndex(int index){
		accountFromComboBox.setSelectedIndex(index);
	}
	public void setAccountToSelectedIndex(int index){
		if (index < accountToComboBox.getItemCount()) {
			accountToComboBox.setSelectedIndex(index);
		}
	}
	public void setClientFromSelectedIndex(int index){
		if (index < clientFromComboBox.getItemCount()) {
			clientFromComboBox.setSelectedIndex(index);
		}
	}
	public void setClientToSelectedIndex(int index){
		if (index < clientToComboBox.getItemCount()) {
			clientToComboBox.setSelectedIndex(index);
		}
	}
	public void setAccountTypeSelectedIndex(int index){
		if (index < accountType.getItemCount()) {
			accountType.setSelectedIndex(index);
		}
	}
	public void setAccountFromItemListener(ItemListener listener){
		accountFromComboBox.addItemListener(listener);
	}
	public void setAccountToItemListener(ItemListener listener){
		accountToComboBox.addItemListener(listener);
	}
	public void setClientFromItemListener(ItemListener listener){
		clientFromComboBox.addItemListener(listener);
	}
	public void setClientToItemListener(ItemListener listener){
		clientToComboBox.addItemListener(listener);
	}
	public void setAccountTypeItemListener(ItemListener listener){
		accountType.addItemListener(listener);
	}
	
	private void initialize() {
		btnSwitchView = new JButton("Switch View");
		btnSwitchView.setBounds(468, 25, 117, 25);
		add(btnSwitchView);
		
		btnLogOut = new JButton("Log Out");
		btnLogOut.setBounds(611, 25, 97, 25);
		add(btnLogOut);
		
		lblClientTo = new JLabel("Client to");
		lblClientTo.setBounds(22, 74, 70, 21);
		add(lblClientTo);
		
		clientToComboBox = new JComboBox();
		clientToComboBox.setBounds(22, 108, 117, 22);
		add(clientToComboBox);
		
		lblAccountTo = new JLabel("Account to");
		lblAccountTo.setBounds(22, 143, 97, 16);
		add(lblAccountTo);
		
		accountToComboBox = new JComboBox();
		accountToComboBox.setBounds(22, 172, 117, 22);
		add(accountToComboBox);
		
		lblBill = new JLabel("Bill");
		lblBill.setBounds(22, 361, 56, 16);
		add(lblBill);
		
		lblCompany = new JLabel("Company");
		lblCompany.setBounds(22, 390, 56, 16);
		add(lblCompany);
		
		companyTextField = new JTextField();
		companyTextField.setBounds(23, 419, 116, 22);
		add(companyTextField);
		companyTextField.setColumns(10);
		
		btnTransfer = new JButton("Transfer");
		btnTransfer.setBounds(22, 281, 97, 25);
		add(btnTransfer);
		
		amountTextField = new JTextField();
		amountTextField.setBounds(23, 246, 116, 22);
		add(amountTextField);
		amountTextField.setColumns(10);
		
		lblAmmount = new JLabel("Ammount");
		lblAmmount.setBounds(22, 217, 70, 16);
		add(lblAmmount);
		
		lblTotal = new JLabel("Total");
		lblTotal.setBounds(22, 468, 56, 16);
		add(lblTotal);
		
		totalTextField = new JTextField();
		totalTextField.setBounds(23, 497, 116, 22);
		add(totalTextField);
		totalTextField.setColumns(10);
		
		lblClientfrom = new JLabel("Client (From)");
		lblClientfrom.setBounds(325, 76, 88, 16);
		add(lblClientfrom);
		
		clientFromComboBox = new JComboBox();
		clientFromComboBox.setBounds(325, 108, 117, 22);
		add(clientFromComboBox);
		
		lblName = new JLabel("Name");
		lblName.setBounds(207, 175, 56, 16);
		add(lblName);
		
		name = new JTextField();
		name.setBounds(326, 172, 116, 22);
		add(name);
		name.setColumns(10);
		
		lblAddress = new JLabel("Address");
		lblAddress.setBounds(207, 228, 56, 16);
		add(lblAddress);
		
		address = new JTextField();
		address.setBounds(326, 225, 116, 22);
		add(address);
		address.setColumns(10);
		
		lblPersonalNumericalCode = new JLabel("Personal Numerical Code");
		lblPersonalNumericalCode.setBounds(174, 285, 150, 16);
		add(lblPersonalNumericalCode);
		
		personalNumericalCode = new JTextField();
		personalNumericalCode.setBounds(325, 279, 116, 22);
		add(personalNumericalCode);
		personalNumericalCode.setColumns(10);
		
		lblIdCardNumber = new JLabel("ID Card Number");
		lblIdCardNumber.setBounds(207, 329, 103, 16);
		add(lblIdCardNumber);
		
		idCardNumber = new JTextField();
		idCardNumber.setBounds(326, 326, 116, 22);
		add(idCardNumber);
		idCardNumber.setColumns(10);
		
		lblAccountfrom = new JLabel("Account (From)");
		lblAccountfrom.setBounds(604, 76, 126, 16);
		add(lblAccountfrom);
		
		accountFromComboBox = new JComboBox();
		accountFromComboBox.setBounds(591, 108, 117, 22);
		add(accountFromComboBox);
		
		lblAccountId = new JLabel("Account ID");
		lblAccountId.setBounds(492, 175, 78, 16);
		add(lblAccountId);
		
		lblType = new JLabel("Type");
		lblType.setBounds(492, 228, 56, 16);
		add(lblType);
		
		lblCreationDate = new JLabel("Creation Date");
		lblCreationDate.setBounds(492, 282, 78, 16);
		add(lblCreationDate);
		
		lblBalance = new JLabel("Balance");
		lblBalance.setBounds(492, 329, 56, 16);
		add(lblBalance);
		
		accountID = new JTextField();
		accountID.setBounds(591, 172, 116, 22);
		add(accountID);
		accountID.setColumns(10);
		
		accountType = new JComboBox();
		accountType.setBounds(591, 225, 117, 22);
		add(accountType);
		
		balance = new JTextField();
		balance.setBounds(591, 326, 116, 22);
		add(balance);
		balance.setColumns(10);
		
		btnAddClient = new JButton("Add Client");
		btnAddClient.setBounds(316, 418, 126, 25);
		add(btnAddClient);
		
		btnUpdateClient = new JButton("Update Client");
		btnUpdateClient.setBounds(316, 464, 126, 25);
		add(btnUpdateClient);
		
		btnRemoveClient = new JButton("Remove Client");
		btnRemoveClient.setBounds(316, 513, 126, 25);
		add(btnRemoveClient);
		
		btnPayBill = new JButton("Pay Bill");
		btnPayBill.setBounds(22, 532, 97, 25);
		add(btnPayBill);
		
		btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.setBounds(576, 418, 132, 25);
		add(btnCreateAccount);
		
		btnUpdateAccount = new JButton("Update Account");
		btnUpdateAccount.setBounds(576, 464, 132, 25);
		add(btnUpdateAccount);
		
		btnRemoveAccount = new JButton("Remove Account");
		btnRemoveAccount.setBounds(576, 513, 132, 25);
		add(btnRemoveAccount);
		
		creationDate = new JTextField();
		creationDate.setBounds(592, 282, 116, 22);
		add(creationDate);
		creationDate.setColumns(10);
	}
}