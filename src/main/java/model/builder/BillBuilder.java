package model.builder;

import model.Bill;
import model.User;

public class BillBuilder {
    private Bill bill;

    public BillBuilder(){
        this.bill = new Bill();
    }

    public BillBuilder setCompany(String company){
        bill.setCompany(company);
        return this;
    }

    public BillBuilder setSum(double sum){
        bill.setSum(sum);
        return this;
    }

    public BillBuilder setId(Long id){
        bill.setId(id);
        return this;
    }

    public BillBuilder setUser(User user){
        bill.setUser(user);
        return this;
    }

    public Bill build(){
        return bill;
    }
}
