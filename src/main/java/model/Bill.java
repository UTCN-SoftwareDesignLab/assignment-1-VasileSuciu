package model;

public class Bill {

    private Long id;
    private double sum;
    private String company;
    private User user;


    public Long getId() { return id; }

    public double getSum() { return sum; }

    public String getCompany() { return company; }

    public User getUser() { return user; }

    public void setId(Long id) { this.id = id; }

    public void setSum(double sum) { this.sum = sum; }

    public void setCompany(String company) { this.company = company; }

    public void setUser(User user) { this.user = user; }


}


