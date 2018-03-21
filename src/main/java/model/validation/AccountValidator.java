package model.validation;

import database.Constants;
import model.Account;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AccountValidator {
    private final Account account;

    private List<String> errors;

    public AccountValidator(Account account){
        this.account = account;
        errors = new ArrayList<String>();
    }

    public boolean validate(){
        validateType(account.getType());
        validateCreationDate(account.getCreationDate());
        validateBalance(account.getType(), account.getBalance());
        return errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }

    public boolean hasErrors(){
        return !errors.isEmpty();
    }

    private void validateType(String type){
        if (!(type.trim().toLowerCase().equals(Constants.AccountTypes.DEBIT.toLowerCase()) ||
            type.trim().toLowerCase().equals(Constants.AccountTypes.CREDIT.toLowerCase()))) {
            errors.add("An account can be only of debit or credit!");
        }
    }

    private void validateCreationDate(Date date){
        Date today=new Date(System.currentTimeMillis());
        if (date.after(today)) {
            errors.add("Account creation date cannot be newer then today!");
        }
    }

    public void validateBalance(String type, double balance){
        if (type.trim().equals(Constants.AccountTypes.DEBIT.toLowerCase()) && balance < 0){
            errors.add("A debit account cannot have a negative balance!");
        }
    }
}
