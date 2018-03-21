package model.validation;

import model.Bill;

import java.util.ArrayList;
import java.util.List;

public class BillValidator {

    private final Bill bill;
    private List<String> errors;

    public BillValidator(Bill bill) {
        this.bill = bill;
        errors = new ArrayList<String>();
    }

    public boolean validate(){
        if (bill.getSum() < 0){
            errors.add("Bill value cannot be negative!");
        }

        if (bill.getCompany() == null || bill.getCompany().trim().length()<1){
            errors.add("Bill must be registered to a company");
        }

        if (bill.getUser() == null){
            errors.add("Cashier performing the payment must be registered!");
        }
        return errors.isEmpty();
    }

    public boolean hasErrors(){
        return !errors.isEmpty();
    }

    public List<String> getErrors(){
        return errors;
    }
}
