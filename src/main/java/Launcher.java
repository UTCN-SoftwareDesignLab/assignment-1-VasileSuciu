import controllers.Controller;
import views.*;

public class Launcher {
    public static void main(String[] args) {
        MainFrame mainFrame =new MainFrame();
        EmployeePanel employeePanel = new EmployeePanel();
        LogInPanel logInPanel = new LogInPanel();
        AdministratorPanel administratorPanel = new AdministratorPanel();
        ComponentFactory componentFactory =ComponentFactory.getInstance();
        Controller controller = new Controller(componentFactory.getAccountManagementService(),
                componentFactory.getAccountTransferService(), componentFactory.getClientManagementService(),
                componentFactory.getBillPaymentService(),componentFactory.getTransactionService(),
                componentFactory.getAuthenticationService(),componentFactory.getUserManagementService(),
                mainFrame,logInPanel,employeePanel, administratorPanel);


    }
}
