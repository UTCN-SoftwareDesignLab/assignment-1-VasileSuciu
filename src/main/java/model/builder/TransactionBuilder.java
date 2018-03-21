package model.builder;

import model.Transaction;
import model.User;

import java.sql.Date;

public class TransactionBuilder {
    public Transaction transaction;

    public TransactionBuilder(){
        this.transaction = new Transaction();
    }

    public TransactionBuilder setType(String type){
        this.transaction.setType(type);
        return this;
    }

    public TransactionBuilder setUser(User user){
        transaction.setUser(user);
        return this;
    }

    public TransactionBuilder setDate(Date date){
        transaction.setDate(date);
        return this;
    }

    public TransactionBuilder setId(Long id){
        transaction.setId(id);
        return this;
    }

    public Transaction build(){
        return transaction;
    }
}
