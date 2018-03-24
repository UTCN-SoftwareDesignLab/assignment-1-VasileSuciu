package repository.bank;

import model.Client;

import java.util.List;

public interface ClientRepository {

    boolean addClient(Client client);

    boolean removeClient(Client client);

    boolean updateClient(Client client);

    List<Client> getAllClients();

    Client getClientById(Long id);

    Client getClientByName(String name);
}
