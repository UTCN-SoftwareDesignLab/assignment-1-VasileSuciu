package service.bill;

import model.Bill;
import model.User;
import model.builder.BillBuilder;
import model.validation.BillValidator;
import model.validation.Notification;
import repository.bill.BillRepository;

public class BillPaymentServiceMySQL implements BillPaymentService {

    private BillRepository billRepository;

    public BillPaymentServiceMySQL(BillRepository billRepository){
        this.billRepository = billRepository;
    }

    @Override
    public Notification<Boolean> payBill(User user, String company, double sum) {
        Bill bill = new BillBuilder()
                    .setCompany(company)
                    .setSum(sum)
                    .setUser(user)
                    .build();
        BillValidator billValidator = new BillValidator(bill);
        boolean billValid = billValidator.validate();
        Notification<Boolean> notification = new Notification<Boolean>();
        if (billValid){
            notification.setResult(billRepository.addBill(bill));
        }
        else {
            billValidator.getErrors().forEach(notification::addError);
            notification.setResult(Boolean.FALSE);
        }
        return notification;
    }
}
