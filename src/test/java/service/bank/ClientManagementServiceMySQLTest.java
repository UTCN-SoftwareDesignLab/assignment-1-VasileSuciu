package service.bank;

import database.DBConnectionFactory;
import database.JDBConnectionWrapper;
import model.Client;
import model.validation.Notification;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.bank.ClientRepository;
import repository.bank.ClientRepositoryMySQL;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.*;

public class ClientManagementServiceMySQLTest {
    private static ClientManagementService clientManagementService;
    private static ClientRepository clientRepository;

    @Before
    public void setUp() throws Exception {
        Connection connection =new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        clientRepository = new ClientRepositoryMySQL(connection);
        clientManagementService = new ClientManagementServiceMySQL(clientRepository);
        clientManagementService.registerClient("TestClient0","None","12345","GG111");
        clientManagementService.registerClient("TestClient1","None","12345","GG111");
    }

    @After
    public void tearDown() throws Exception {
        clientManagementService.deleteClient("TestClient0");
        clientManagementService.deleteClient("TestClient1");
        clientManagementService.deleteClient("TestClient");
    }

    @Test
    public void registerClient(){
        Assert.assertTrue(clientManagementService.registerClient("TestClient","None","12345","GG111")
                       .getResult());
    }

    @Test
    public void updateClient(){
        Assert.assertTrue(clientManagementService.updateClient("TestClient0","None","12345","GG222")
                .getResult());
    }

    @Test
    public void deleteClient(){
        Assert.assertTrue(clientManagementService.deleteClient("TestClient1"));
    }

    @Test
    public void getClient(){
        Assert.assertNotNull(clientManagementService.getClient("TestClient0"));
    }

    @Test
    public void getAllClientsName(){
        Assert.assertTrue(clientManagementService.getAllClientsName().size() > 1);
    }


}