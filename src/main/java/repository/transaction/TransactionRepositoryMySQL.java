package repository.transaction;

import model.Transaction;
import model.User;
import model.builder.TransactionBuilder;
import repository.user.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepositoryMySQL implements TransactionRepository {

    private Connection connection;
    private UserRepository userRepository;

    public TransactionRepositoryMySQL(Connection connection, UserRepository userRepository){
        this.connection = connection;
        this.userRepository = userRepository;
    }

    @Override
    public boolean addTransaction(Transaction transaction) {
        try{
            PreparedStatement addTransaction = connection.prepareStatement(
                    "INSERT INTO `transaction`(transaction_id, type, date, user_id)" +
                            " VALUES (null, ?, ?, ?)");
            addTransaction.setString(1, transaction.getType());
            addTransaction.setDate(2,transaction.getDate());
            addTransaction.setLong(3,transaction.getUser().getId());
            addTransaction.executeUpdate();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public List<Transaction> getAllTransaction() {
        List<Transaction> transactions = new ArrayList<Transaction>();
        try{
            PreparedStatement findTransaction = connection.prepareStatement(
                    "SELECT * from `TRANSACTION`");
            ResultSet rs = findTransaction.executeQuery();
            while (rs.next()){
                transactions.add(getTransactionFromResultSet(rs));
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public List<Transaction> findTransactionByUserName(String user) {
        List<Transaction> transactions = new ArrayList<Transaction>();
        Long userID = userRepository.findByUsername(user).getId();
        try{
            PreparedStatement findTransaction = connection.prepareStatement(
                    "SELECT * from `TRANSACTION` WHERE user_id = ? ");
            findTransaction.setLong(1,userID);
            ResultSet rs = findTransaction.executeQuery();
            while (rs.next()){
                transactions.add(getTransactionFromResultSet(rs));
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public Transaction findTransactionById(Long id) {
        try{
            PreparedStatement findTransaction = connection.prepareStatement(
                    "SELECT * from `TRANSACTION` WHERE transaction_id = ? ");
            findTransaction.setLong(1,id);
            ResultSet rs = findTransaction.executeQuery();
            if (rs.next()){
                return getTransactionFromResultSet(rs);
            }
            else{
                return null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void removeAllTransactions() {
        try{
            PreparedStatement deleteAll = connection.prepareStatement("" +
                    "DELETE FROM transaction WHERE id>=0");
            deleteAll.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private Transaction getTransactionFromResultSet(ResultSet rs) throws SQLException{
        Transaction transaction = new TransactionBuilder()
                .setId(rs.getLong("transaction_id"))
                .setType(rs.getString("type"))
                .setDate(rs.getDate("date"))
                .setUser(userRepository.findById(rs.getLong("user_id")))
                .build();
        return transaction;

    }
}
