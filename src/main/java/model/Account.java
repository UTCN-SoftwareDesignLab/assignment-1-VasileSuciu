package model;

import java.util.Date;
import java.util.Objects;

public class Account {

    private Long id;
    private String type;
    private Date creationDate;
    private double balance;
    private Client client;

    public Account(){
        this.creationDate = new Date();
    }

    public Long getId() { return id; }

    public String getType() { return type; }

    public double getBalance() { return balance; }

    public Client getClient() { return client; }

    public Date getCreationDate() { return creationDate; }

    public void setId(Long id) { this.id = id; }

    public void setType(String type) { this.type = type; }

    public void setBalance(double balance) { this.balance = balance; }

    public void setClient(Client client) { this.client = client; }

    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
