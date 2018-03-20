package database;

import java.security.cert.CRLReason;
import java.util.*;

import static database.Constants.Rights.*;
import static database.Constants.Roles.*;

public class Constants {

    public static class Schemas {
        public static final String TEST = "test_bank";
        public static final String PRODUCTION = "bank";

        public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
    }

    public static class Tables {
        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String RIGHTS = "rights";
        public static final String ROLE_RIGHT = "role_right";
        public static final String USER_ROLE = "user_role";
        public static final String TRANSACTION = "transaction";
        public static final String CLIENT = "client";
        public static final String ACCOUNT = "account";
        public static final String BILL = "bill";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ROLE, RIGHTS, ROLE_RIGHT, USER_ROLE, TRANSACTION,
                                                                    CLIENT, ACCOUNT, BILL};
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE};
    }

    public static class Rights {
        public static final String CREATE_USER = "create_user";
        public static final String DELETE_USER = "delete_user";
        public static final String UPDATE_USER = "update_user";

        public static final String CREATE_CLIENT = "create_client";
        public static final String DELETE_CLIENT = "delete_client";
        public static final String UPDATE_CLIENT = "update_client";
        public static final String VIEW_CLIENT = "view_client";

        public static final String CREATE_ACCOUNT = "create_account";
        public static final String DELETE_ACCOUNT = "delete_account";
        public static final String UPDATE_ACCOUNT = "update_account";
        public static final String VIEW_ACCOUNT = "view_account";

        public static final String PROCESS_BILLS = "process_bills";
        public static final String TRANSFER_MONEY = "transfer_money";
        public static final String CREATE_REPORT = "create_report";

        public static final String[] RIGHTS = new String[]{CREATE_USER, DELETE_USER, UPDATE_USER, CREATE_CLIENT, DELETE_CLIENT, UPDATE_CLIENT,
                                                            VIEW_CLIENT, CREATE_ACCOUNT, DELETE_ACCOUNT, UPDATE_ACCOUNT, VIEW_ACCOUNT, PROCESS_BILLS,
                                                            TRANSFER_MONEY, CREATE_REPORT};
    }

    public static class AccountTypes{
        public static final String DEBIT = "debit";
        public static final String CREDIT ="credit";
    }

    public static class TransactionType{
        public static final String ACCOUNTS_TRANSFER = "accounts transfer";
        public static final String PAYING_BILL = "paying bill";
        public static final String CLIENT_CREATION = "client creation";
        public static final String CLIENT_UPDATE = "client update";
        public static final String CLIENT_REMOVAL = "client removal";
        public static final String ACCOUNT_CREATION = "account creation";
        public static final String ACCOUNT_UPDATE = "account update";
        public static final String ACCOUNT_REMOVAL = "account removal";
    }

    public static Map<String, List<String>> getRolesRights() {
        Map<String, List<String>> ROLES_RIGHTS = new HashMap<>();
        for (String role : ROLES) {
            ROLES_RIGHTS.put(role, new ArrayList<>());
        }
        ROLES_RIGHTS.get(ADMINISTRATOR).addAll(Arrays.asList(CREATE_USER, DELETE_USER, UPDATE_USER, CREATE_REPORT));

        ROLES_RIGHTS.get(EMPLOYEE).addAll(Arrays.asList(CREATE_CLIENT, DELETE_CLIENT, UPDATE_CLIENT,VIEW_CLIENT,CREATE_ACCOUNT,
                                                        DELETE_ACCOUNT, UPDATE_ACCOUNT, VIEW_ACCOUNT, PROCESS_BILLS, TRANSFER_MONEY));

        return ROLES_RIGHTS;
    }

}
