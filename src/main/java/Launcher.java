import controllers.*;
import views.*;

public class Launcher {
    public static void main(String[] args) {
        MainFrame mainFrame =new MainFrame();
        EmployeePanel employeePanel = new EmployeePanel();
        LogInPanel logInPanel = new LogInPanel();
        AdministratorPanel administratorPanel = new AdministratorPanel();
        ComponentFactory componentFactory =ComponentFactory.getInstance();

        LogInController logInController = new LogInController(logInPanel, componentFactory.getAuthenticationService());
        AdministratorController administratorController = new AdministratorController(administratorPanel, componentFactory.getAuthenticationService(),
                componentFactory.getUserManagementService(), componentFactory.getTransactionService());
        EmployeeController employeeController = new EmployeeController(employeePanel,componentFactory.getAccountManagementService(),
                componentFactory.getAccountTransferService(), componentFactory.getClientManagementService(),
                componentFactory.getBillPaymentService(), componentFactory.getTransactionService());

        MainController mainController = new MainController(mainFrame,employeeController, logInController, administratorController);

        /*Controller controller = new Controller(componentFactory.getAccountManagementService(),
                componentFactory.getAccountTransferService(), componentFactory.getClientManagementService(),
                componentFactory.getBillPaymentService(),componentFactory.getTransactionService(),
                componentFactory.getAuthenticationService(),componentFactory.getUserManagementService(),
                mainFrame,logInPanel,employeePanel, administratorPanel);*/



    }
}
