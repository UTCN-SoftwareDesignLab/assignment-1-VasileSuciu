package repository.bank;

import model.Client;
import model.builder.ClientBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryMySQL implements ClientRepository {
    private Connection connection;

    public ClientRepositoryMySQL(Connection connection){
        this.connection = connection;
    }

    @Override
    public boolean addClient(Client client) {
        try{
            PreparedStatement addClient = connection.prepareStatement(
                    "INSERT INTO client (client_id, id_card_num, personal_num_code, address, name)" +
                            " VALUES (null, ?, ?, ?, ?)");
            addClient.setString(1,client.getIdCardNumber());
            addClient.setString(2,client.getPersonalNumericalCode());
            addClient.setString(3,client.getAddress());
            addClient.setString(4,client.getName());
            addClient.executeUpdate();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeClient(Client client) {
        try{
            PreparedStatement removeClient = connection.prepareStatement(
                    "DELETE FROM  client WHERE client_id = ?");
            removeClient.setLong(1,client.getId());
            removeClient.executeUpdate();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateClient(Client client) {
        try{
            PreparedStatement updateClient = connection.prepareStatement(
                    "UPDATE  client " +
                            " SET id_card_num = ?, personal_num_code = ?, address = ?, name = ?" +
                            " WHERE client_id = ?");
            updateClient.setString(1,client.getIdCardNumber());
            updateClient.setString(2,client.getPersonalNumericalCode());
            updateClient.setString(3,client.getAddress());
            updateClient.setString(4,client.getName());
            updateClient.setLong(5,client.getId());
            updateClient.executeUpdate();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<Client>();
        try{
            PreparedStatement getClient = connection.prepareStatement(
                    "SELECT  * FROM client");
            ResultSet rs = getClient.executeQuery();
            while (rs.next()){
                clients.add(getClientFromResultSet(rs));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public Client getClientById(Long id) {
        try{
            PreparedStatement getClient = connection.prepareStatement(
                    "SELECT  * FROM client WHERE client_id = ?");
            getClient.setLong(1,id);
            ResultSet rs = getClient.executeQuery();
            if (rs.next()){
                return getClientFromResultSet(rs);
            }
            else{
                return null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Client> getClientsByName(String name) {
        List<Client> clients = new ArrayList<Client>();
        try{
            PreparedStatement getClient = connection.prepareStatement(
                    "SELECT  * FROM client WHERE name = ?");
            getClient.setString(1,name);
            ResultSet rs = getClient.executeQuery();
            while (rs.next()){
                clients.add(getClientFromResultSet(rs));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return clients;
    }

    private Client getClientFromResultSet(ResultSet rs) throws SQLException {
        return new ClientBuilder()
                .setIdCardNumber(rs.getString("id_card_num"))
                .setPersonalNumericalCode(rs.getString("personal_num_code"))
                .setAddress(rs.getString("address"))
                .setName(rs.getString("name"))
                .setId(rs.getLong("client_id"))
                .build();
    }
}
