package service.transaction;

import model.Transaction;
import model.User;
import model.builder.TransactionBuilder;
import repository.transaction.TransactionRepository;

import java.sql.Date;
import java.util.List;

public class TransactionServiceMySQL  implements TransactionService{
    private TransactionRepository transactionRepository;

    public TransactionServiceMySQL(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    @Override
    public boolean recordTransaction(User user, String type, Date date) {
        Transaction transaction = new TransactionBuilder()
                .setUser(user)
                .setDate(date)
                .setType(type)
                .build();
        return transactionRepository.addTransaction(transaction);
    }

    @Override
    public String getReportForUser(String user) {
        StringBuilder report = new StringBuilder();
        List<Transaction> transactions = transactionRepository.findTransactionByUserName(user);
        transactions.forEach(s -> report.append(s.toString()+"\n"));
        return report.toString();
    }
}
