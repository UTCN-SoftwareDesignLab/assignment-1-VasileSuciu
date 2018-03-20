package model.builder;

import model.Bill;

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

    public Bill build(){
        return bill;
    }
}
