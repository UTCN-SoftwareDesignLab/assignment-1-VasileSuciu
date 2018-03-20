package repository.bank;

import model.Account;
import model.Client;

import java.util.List;

public interface AccountRepository {

    void addAccount(Account account);

    boolean removeAccount(Account account);

    boolean updateAccount(Account account);

    List<Account> getAllAccounts();

    Account getAccountById(Long id);

    List<Account> getAccountByClient(Client client);

}
