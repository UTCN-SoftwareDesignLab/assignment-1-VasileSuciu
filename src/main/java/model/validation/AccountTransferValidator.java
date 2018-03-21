package model.validation;

import database.Constants;
import model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountTransferValidator {
    private Account accountFrom;
    private double sum;
    private List<String> errors;

    public AccountTransferValidator(Account accountFrom, double sum){
        this.accountFrom = accountFrom;
        this.sum = sum;
        errors = new ArrayList<String>();
    }

    public boolean validate(){
        if (accountFrom.getType().equals(Constants.AccountTypes.DEBIT) && sum > accountFrom.getBalance()){
            errors.add("Transfer sum greater than account balance");
        }
        return errors.isEmpty();
    }

    public boolean hasErrors(){
        return !errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }
}
