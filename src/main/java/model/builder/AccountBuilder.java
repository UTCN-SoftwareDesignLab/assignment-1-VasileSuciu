package model.builder;

import database.Constants;
import model.Account;
import model.Client;

public class AccountBuilder {
    private Account account;

    public AccountBuilder(){
        this.account = new Account();
    }

    public AccountBuilder setType(String type){
        account.setType(type);
        return this;
    }

    public AccountBuilder setBalance(double balance){
        account.setBalance(balance);
        return this;
    }

    public AccountBuilder setClient(Client client){
        account.setClient(client);
        return  this;
    }

    public Account build(){
        return account;
    }
}
