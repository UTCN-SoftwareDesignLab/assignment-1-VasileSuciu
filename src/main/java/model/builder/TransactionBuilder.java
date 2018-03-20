package model.builder;

import model.Transaction;
import model.User;

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

    public Transaction build(){
        return transaction;
    }
}
