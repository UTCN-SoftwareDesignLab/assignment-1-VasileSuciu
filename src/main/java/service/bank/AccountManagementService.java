package service.bank;

import model.Account;
import model.validation.Notification;

import java.sql.Date;
import java.util.List;

public interface AccountManagementService {

    Notification<Boolean> createAccount(String client, String type, double balance, Date creationDate);

    Notification<Boolean> updateAccount(Long id, String type, double balance);

    boolean removeAccount(Long id);

    Account getAccountById(Long id);

    List<Account> getAccountsByClient(String client);
}
