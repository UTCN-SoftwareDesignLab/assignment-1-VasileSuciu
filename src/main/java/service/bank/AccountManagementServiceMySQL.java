package service.bank;

import model.Account;
import model.builder.AccountBuilder;
import model.validation.AccountValidator;
import model.validation.Notification;
import repository.bank.AccountRepository;
import repository.bank.ClientRepository;

import java.sql.Date;
import java.util.List;


public class AccountManagementServiceMySQL implements AccountManagementService {
    private AccountRepository accountRepository;
    private ClientRepository clientRepository;

    public AccountManagementServiceMySQL(AccountRepository accountRepository, ClientRepository clientRepository){
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Notification<Boolean> createAccount(String client, String type, double balance, Date creationDate) {
        Account account = new AccountBuilder()
                .setClient(clientRepository.getClientByName(client))
                .setType(type)
                .setBalance(balance)
                .setCreationDate(creationDate)
                .build();
        AccountValidator accountValidator = new AccountValidator(account);
        boolean accountValid = accountValidator.validate();
        Notification<Boolean> accountNotification = new Notification<>();
        if (accountValid){
            accountNotification.setResult(accountRepository.addAccount(account));
        }
        else{
            accountValidator.getErrors().forEach(accountNotification::addError);
            accountNotification.setResult(Boolean.FALSE);
        }
        return accountNotification;
    }

    @Override
    public Notification<Boolean> updateAccount(Long id, String type, double balance) {
        Account account = accountRepository.getAccountById(id);
        account.setType(type);
        account.setBalance(balance);
        AccountValidator accountValidator = new AccountValidator(account);
        boolean accountValid = accountValidator.validate();
        Notification<Boolean> accountNotification = new Notification<>();
        if (accountValid){
            accountNotification.setResult(accountRepository.updateAccount(account));
        }
        else{
            accountValidator.getErrors().forEach(accountNotification::addError);
            accountNotification.setResult(Boolean.FALSE);
        }
        return accountNotification;
    }

    @Override
    public boolean removeAccount(Long id) {
        return accountRepository.removeAccount(accountRepository.getAccountById(id));
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.getAccountById(id);
    }

    @Override
    public List<Account> getAccountsByClient(String client) {
        return accountRepository.getAccountByClient(clientRepository.getClientByName(client));
    }
}
