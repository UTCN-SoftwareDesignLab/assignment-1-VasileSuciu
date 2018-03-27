package service.transaction;

import model.User;

import java.sql.Date;

public interface TransactionService {

    boolean recordTransaction(User user, String type, Date date);

    String getReportForUser(String user);
}
