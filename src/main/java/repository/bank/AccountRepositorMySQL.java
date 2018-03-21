package repository.bank;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositorMySQL implements  AccountRepository{
    private Connection connection;
    private ClientRepository clientRepository;

    public AccountRepositorMySQL(Connection connection, ClientRepository clientRepository){
        this.connection = connection;
        this.clientRepository = clientRepository;
    }

    @Override
    public boolean addAccount(Account account) {
        try{
            PreparedStatement addAccount = connection.prepareStatement("" +
                    "INSERT  INTO account(account_id, type, creation_date, balance, client_id) " +
                    "VALUES (null, ?, ?, ?, ?)");
            addAccount.setString(1,account.getType());
            addAccount.setDate(2,account.getCreationDate());
            addAccount.setDouble(3,account.getBalance());
            addAccount.setLong(4,account.getClient().getId());
            addAccount.executeUpdate();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeAccount(Account account) {
        try{
            PreparedStatement deleteAccount = connection.prepareStatement("" +
                    "DELETE FROM account where account_id = ?");
            deleteAccount.setLong(1,account.getId());
            deleteAccount.executeUpdate();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateAccount(Account account) {
        try{
            PreparedStatement updateAccount= connection.prepareStatement("" +
                    "UPDATE  account " +
                    " SET type = ?, creation_date = ?, balance = ?, client_id = ? " +
                    " WHERE account_id = ?");
            updateAccount.setString(1,account.getType());
            updateAccount.setDate(2,account.getCreationDate());
            updateAccount.setDouble(3,account.getBalance());
            updateAccount.setLong(4,account.getClient().getId());
            updateAccount.setLong(5,account.getId());
            updateAccount.executeUpdate();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<Account>();
        try{
            PreparedStatement getAccount = connection.prepareStatement(
                    "SELECT * FROM account ");
            ResultSet rs = getAccount.executeQuery();
            while (rs.next()){
                accounts.add(getAccountFromResultSet(rs));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Account getAccountById(Long id) {
        try{
            PreparedStatement getAccount = connection.prepareStatement(
                    "SELECT * FROM account WHERE account_id = ?");
            getAccount.setLong(1,id);
            ResultSet rs = getAccount.executeQuery();
            if (rs.next()){
                return getAccountFromResultSet(rs);
            }
            else {
                return null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Account> getAccountByClient(Client client) {
        List<Account> accounts = new ArrayList<Account>();
        try{
            PreparedStatement getAccount = connection.prepareStatement(
                    "SELECT * FROM account WHERE client_id = ?");
            getAccount.setLong(1,client.getId());
            ResultSet rs = getAccount.executeQuery();
            while (rs.next()){
                accounts.add(getAccountFromResultSet(rs));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return accounts;
    }

    private Account getAccountFromResultSet(ResultSet rs) throws SQLException{
        return new AccountBuilder()
                .setBalance(rs.getDouble("balance"))
                .setType(rs.getString("type"))
                .setId(rs.getLong("account_id"))
                .setCreationDate(rs.getDate("creation_date"))
                .setClient(clientRepository.getClientById(rs.getLong("client_id")))
                .build();
    }
}
