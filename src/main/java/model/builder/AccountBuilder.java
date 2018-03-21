package model.builder;

import database.Constants;
import model.Account;
import model.Client;

import java.sql.Date;

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

    public AccountBuilder setCreationDate(Date date){
        account.setCreationDate(date);
        return this;
    }

    public AccountBuilder setId(Long id){
        account.setId(id);
        return this;
    }

    public Account build(){
        return account;
    }
}
