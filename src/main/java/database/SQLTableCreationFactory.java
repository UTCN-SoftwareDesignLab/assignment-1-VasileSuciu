package database;

import static database.Constants.Tables.*;

public class SQLTableCreationFactory {

    public String getCreateSQLForTable(String table) {
        switch(table){
            case USER:
                return "CREATE TABLE IF NOT EXISTS user ("+
                    "user_id INT AUTO_INCREMENT NOT NULL,"+
                    "username VARCHAR(100) NOT NULL,"+
                    "password VARCHAR(100) NOT NULL,"+
                    "PRIMARY KEY (user_id));";

            case ROLE:
                return"CREATE TABLE IF NOT EXISTS role ("+
                    "role_id INT AUTO_INCREMENT NOT NULL,"+
                    "role VARCHAR(100) NOT NULL,"+
                    "PRIMARY KEY (role_id));";

            case RIGHTS:
                return "CREATE TABLE IF NOT EXISTS rights ("+
                    "right_id INT AUTO_INCREMENT NOT NULL,"+
                    "`right` VARCHAR(255) NOT NULL,"+
                    "PRIMARY KEY (right_id));";

            case USER_ROLE:
                return "CREATE TABLE IF NOT EXISTS user_role ("+
                    "user_id INT NOT NULL,"+
                    "role_id INT NOT NULL,"+
                    "PRIMARY KEY (user_id, role_id),"+
                    "CONSTRAINT user_user_role_fk"+
                    "FOREIGN KEY (user_id)"+
                    "REFERENCES user (user_id)"+
                    "ON DELETE NO ACTION"+
                    "ON UPDATE NO ACTION,"+
                    "CONSTRAINT role_user_role_fk"+
                    "FOREIGN KEY (role_id)"+
                    "REFERENCES role (role_id)"+
                    "ON DELETE NO ACTION"+
                    "ON UPDATE NO ACTION);";

            case ROLE_RIGHT:
                return "CREATE TABLE IF NOT EXISTS role_right ("+
                    "right_id INT NOT NULL,"+
                    "role_id INT NOT NULL,"+
                    "PRIMARY KEY (right_id, role_id),"+
                    "CONSTRAINT role_role_right_fk"+
                    "FOREIGN KEY (role_id)"+
                    "REFERENCES role (role_id)"+
                    "ON DELETE NO ACTION"+
                    "ON UPDATE NO ACTION,"+
                    "constraint right_role_right_fk"+
                    "FOREIGN KEY (right_id)"+
                    "REFERENCES rights (right_id)"+
                    "ON DELETE NO ACTION"+
                    "ON UPDATE NO ACTION);";

            case TRANSACTION:
                return "CREATE TABLE IF NOT EXISTS transaction (" +
                    "transaction_id INT AUTO_INCREMENT NOT NULL," +
                    "type VARCHAR(100) NOT NULL," +
                    "date DATE NOT NULL," +
                    "user_id INT NOT NULL," +
                    "PRIMARY KEY (transaction_id),"+
                    "CONSTRAINT user_transaction_fk"+
                    "FOREIGN KEY (user_id)"+
                    "REFERENCES user (user_id)"+
                    "ON DELETE NO ACTION"+
                    "ON UPDATE NO ACTION);";

            case CLIENT:
                return "CREATE TABLE IF NOT EXISTS client ("+
                    "client_id INT AUTO_INCREMENT NOT NULL,"+
                    "id_card_num VARCHAR(20) NOT NULL,"+
                    "personal_num_code VARCHAR(20) NOT NULL,"+
                    "address VARCHAR(255) NOT NULL,"+
                    "name VARCHAR(255) NOT NULL,"+
                    "PRIMARY KEY (client_id));";

            case ACCOUNT:
                return "CREATE TABLE IF NOT EXISTS account (" +
                    "account_id INT AUTO_INCREMENT NOT NULL,1" +
                    "type VARCHAR(100) NOT NULL," +
                    "creation_date DATE NOT NULL," +
                    "balance DOUBLE  NOT NULL," +
                    "client_id INT NOT NULL," +
                    "PRIMARY KEY (account_id)," +
                    "CONSTRAINT client_fk" +
                    "FOREIGN KEY (client_id)" +
                    "REFERENCES client (client_id)" +
                    "ON DELETE NO ACTION" +
                    "ON UPDATE NO ACTION);";


            case BILL:
                return "CREATE TABLE IF NOT EXISTS bill ("+
                        "bill_id INT AUTO_INCREMENT NOT NULL,"+
                        "total DOUBLE  NOT NULL,"+
                        "company VARCHAR(255) NOT NULL,"+
                        "user_id INT NOT NULL,"+
                        "PRIMARY KEY (bill_id),"+
                        "CONSTRAINT user_bill_fk"+
                        "FOREIGN KEY (user_id)"+
                        "REFERENCES user (user_id)"+
                        "ON DELETE NO ACTION"+
                        "ON UPDATE NO ACTION);";

            default:
                return " ";
        }
    }
}
