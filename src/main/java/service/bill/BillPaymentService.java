package service.bill;

import model.User;
import model.validation.Notification;

public interface BillPaymentService {

    Notification<Boolean> payBill(User user, String company, double sum);


}
