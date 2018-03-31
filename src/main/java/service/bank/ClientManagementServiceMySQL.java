package service.bank;

import model.Client;
import model.builder.ClientBuilder;
import model.validation.ClientValidator;
import model.validation.Notification;
import repository.bank.ClientRepository;

import java.util.ArrayList;
import java.util.List;

public class ClientManagementServiceMySQL implements ClientManagementService{

    private ClientRepository clientRepository;

    public  ClientManagementServiceMySQL(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Override
    public Notification<Boolean> registerClient(String name, String address, String pnc, String idCard) {
        Client client = new ClientBuilder()
                    .setName(name)
                    .setAddress(address)
                    .setPersonalNumericalCode(pnc)
                    .setIdCardNumber(idCard)
                    .build();
        ClientValidator clientValidator = new ClientValidator(client);
        boolean clientValid = clientValidator.validate();
        Notification<Boolean> clientNotification = new Notification<>();
        if (clientValid){
            clientNotification.setResult(clientRepository.addClient(client));
        }
        else {
            clientValidator.getErrors().forEach(clientNotification::addError);
            clientNotification.setResult(Boolean.FALSE);
        }
        return clientNotification;
    }

    @Override
    public Notification<Boolean> updateClient(String name, String address, String pnc, String idCard) {
        Client client = clientRepository.getClientByName(name);
        client.setAddress(address);
        client.setPersonalNumericalCode(pnc);
        client.setIdCardNumber(idCard);
        ClientValidator clientValidator = new ClientValidator(client);
        boolean clientValid = clientValidator.validate();
        Notification<Boolean> clientNotification = new Notification<>();
        if (clientValid){
            clientNotification.setResult(clientRepository.updateClient(client));
        }
        else {
            clientValidator.getErrors().forEach(clientNotification::addError);
            clientNotification.setResult(Boolean.FALSE);
        }
        return clientNotification;
    }

    @Override
    public boolean deleteClient(String name) {
        Client client = clientRepository.getClientByName(name);
        if (client != null) {
            return clientRepository.removeClient(client);
        }
        return false;
    }

    @Override
    public Client getClient(String name) {
        return clientRepository.getClientByName(name);
    }

    @Override
    public List<String> getAllClientsName() {
        List<String> names = new ArrayList<String>();
        clientRepository.getAllClients().forEach(s-> names.add(s.getName()));
        return names;
    }
}
