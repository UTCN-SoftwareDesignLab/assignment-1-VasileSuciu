package repository.transaction;

import model.Transaction;
import model.User;

import java.util.List;

public interface TransactionRepository {

    boolean addTransaction(Transaction transaction);

    List<Transaction> getAllTransaction();

    List<Transaction> findTransactionByUser(User user);

    Transaction findTransactionById(Long id);

    void removeAllTransactions();
}
