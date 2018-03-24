package service.bank;

import model.Client;
import model.validation.Notification;

import java.util.List;

public interface ClientManagementService {

    Notification<Boolean> registerClient(String name, String address, String pnc, String idCard);

    Notification<Boolean> updateClient(String name, String address, String pnc, String idCard);

    boolean deleteClient(String name);

    Client getClient(String name);

    List<String> getAllClientsName();

}
