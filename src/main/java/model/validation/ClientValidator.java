package model.validation;

import model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {
    private final Client client;
    private List<String> errors;

    public ClientValidator(Client client){
        this.client = client;
        errors = new ArrayList<String>();
    }

    public boolean validate(){
        validateName(client.getName());
        validateAddress(client.getAddress());
        validateIDCardNumber(client.getIdCardNumber());
        validatePersonalNumericalCode(client.getPersonalNumericalCode());
        return errors.isEmpty();
    }

    public List<String> getErrors(){
        return errors;
    }

    public boolean hasErrors(){
        return !errors.isEmpty();
    }

    private void validateName(String name){
        if (name == null || name.trim().length()<1){
            errors.add("Name should not be empty!");
        }
    }

    private void validatePersonalNumericalCode(String pnc){
        if (pnc == null || pnc.trim().length() != 5){
            errors.add("Personal Numerical Code should be 5 digits long");
        }
        for (Character c: pnc.trim().toCharArray()){
            if (!Character.isDigit(c)){
                errors.add("Personal Numerical Code should contain only digits");
                return;
            }
        }
    }

    private void validateIDCardNumber(String idcard){
        if (idcard == null || idcard.trim().length() != 5){
            errors.add("ID Card Number should be 5 characters long");
        }
        char[] chars = idcard.trim().toCharArray();
        if (!Character.isAlphabetic(chars[0]) || !Character.isAlphabetic(chars[1])){
            errors.add("First two characters of ID Card Number should be letters");
        }

        if (!Character.isDigit(chars[2]) || !Character.isDigit(chars[3]) || !Character.isDigit(chars[4])){
            errors.add("Last three characters of ID Card Number should be digits");
        }
    }

    private void validateAddress(String address){
        if (address == null || address.trim().length()<1){
            errors.add("Address should not be empty!");
        }
    }
}
