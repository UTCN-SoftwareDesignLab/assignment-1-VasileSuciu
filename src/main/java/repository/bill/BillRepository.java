package repository.bill;

import model.Bill;
import model.User;

import java.util.List;

public interface BillRepository {

    boolean addBill(Bill bill);

    List<Bill> getAllBills();

    List<Bill> getBillByUser(User user);

    Bill getBillById(Long id);

}
