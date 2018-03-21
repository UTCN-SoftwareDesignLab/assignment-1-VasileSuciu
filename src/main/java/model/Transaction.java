package model;

import java.sql.Date;

public class Transaction{

    private Long id;
    private String type;
    private Date date;
    private User user;

    public Transaction(){
        this.date = new Date(System.currentTimeMillis());
    }

    public void setId(Long id) { this.id = id; }

    public void setType(String type) { this.type = type; }

    public void setDate(Date date) { this.date = date; }

    public void setUser(User user) { this.user = user; }

    public Long getId() { return id; }

    public String getType() { return type; }

    public Date getDate() { return date; }

    public User getUser() { return user; }
}
