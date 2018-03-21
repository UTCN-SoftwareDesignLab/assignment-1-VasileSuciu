package model.builder;

import model.Account;
import model.Client;

import java.util.List;

public class ClientBuilder {
    private Client client;

    public ClientBuilder(){
        this.client = new Client();
    }

    public ClientBuilder setIdCardNumber(String idCardNumber){
        client.setIdCardNumber(idCardNumber);
        return this;
    }

    public ClientBuilder setPersonalNumericalCode(String personalNumericalCode){
        client.setPersonalNumericalCode(personalNumericalCode);
        return this;
    }

    public ClientBuilder setAddress(String address){
        client.setAddress(address);
        return this;
    }

    public ClientBuilder setName(String name){
        client.setName(name);
        return this;
    }

    public ClientBuilder setId(Long id){
        client.setId(id);
        return this;
    }

    public Client build(){
        return client;
    }
}
