package service.bank;

import model.Account;
import model.validation.AccountTransferValidator;
import model.validation.Notification;
import repository.bank.AccountRepository;

public class AccountTransferServiceMySQL implements AccountTransferService {
    private AccountRepository accountRepository;

    public AccountTransferServiceMySQL(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Override
    public Notification<Boolean> moneyTransfer(Long accountFromId, Long accountToId, double sum) {
        Account accountFrom = accountRepository.getAccountById(accountFromId);
        Account accountTo = accountRepository.getAccountById(accountToId);
        AccountTransferValidator accountTransferValidator = new AccountTransferValidator(accountFrom, sum);
        boolean transferValid = accountTransferValidator.validate();
        Notification<Boolean> transferNotification = new Notification<>();
        if (transferValid){
            accountFrom.setBalance(accountFrom.getBalance() - sum);
            accountTo.setBalance(accountTo.getBalance() + sum);
            transferNotification.setResult(accountRepository.updateAccount(accountFrom)
                            && accountRepository.updateAccount(accountTo));
        }
        else {
            accountTransferValidator.getErrors().forEach(transferNotification::addError);
            transferNotification.setResult(Boolean.FALSE);
        }
        return transferNotification;
    }
}
