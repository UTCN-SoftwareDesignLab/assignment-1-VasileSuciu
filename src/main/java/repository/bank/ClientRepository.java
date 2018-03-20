package repository.bank;

import model.Client;

import java.util.List;

public interface ClientRepository {

    void addClient(Client client);

    boolean removeClient(Client client);

    boolean updateClient(Client client);

    List<Client> getAllClients();

    Client getClientById(Long id);

    List<Client> getClientsByName(String name);
}
