package repository.bill;

import model.Bill;
import model.User;
import model.builder.BillBuilder;
import repository.user.UserRepository;

import java.lang.reflect.Executable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillRepositoryMySQL implements BillRepository {

    private final Connection connection;
    private final UserRepository userRepository;

    public BillRepositoryMySQL(Connection connection, UserRepository userRepository){
        this.connection = connection;
        this.userRepository = userRepository;
    }


    @Override
    public boolean addBill(Bill bill) {
        try {
            PreparedStatement insertBill = connection.prepareCall(
                    "INSERT INTO bill (bill_id, total, company, user_id) VALUES (null,?,?,?)");
            insertBill.setDouble(1,bill.getSum());
            insertBill.setString(2,bill.getCompany());
            insertBill.setLong(3, bill.getUser().getId());
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Bill> getAllBills() {

        List<Bill> bills = new ArrayList<Bill>();
        try {
            PreparedStatement selectBill = connection.prepareStatement(
                    "SELECT * FROM bill ");
            ResultSet rs = selectBill.executeQuery();
            while (rs.next()){
                bills.add(getBillFromResultSet(rs));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return bills;
    }

    @Override
    public List<Bill> getBillByUser(User user) {
        List<Bill> bills = new ArrayList<Bill>();
        try {
            PreparedStatement selectBill = connection.prepareStatement(
                    "SELECT * FROM bill WHERE user_id = ?");
            selectBill.setLong(1, user.getId());
            ResultSet rs = selectBill.executeQuery();
            while (rs.next()){
                bills.add(getBillFromResultSet(rs));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return bills;
    }

    @Override
    public Bill getBillById(Long id) {
        try {
            PreparedStatement selectBill = connection.prepareStatement(
                    "SELECT * FROM bill WHERE bill_id = ?");
            selectBill.setLong(1, id);
            ResultSet rs = selectBill.executeQuery();
            if (rs.next()) {
                return getBillFromResultSet(rs);
            }
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private Bill getBillFromResultSet(ResultSet rs) throws SQLException {
        Bill bill = new BillBuilder()
                .setSum(rs.getDouble("total"))
                .setCompany(rs.getString("company"))
                .setId(rs.getLong("bill_id"))
                .setUser(userRepository.findById(rs.getLong("user_id")))
                .build();
        return bill;
    }
}
