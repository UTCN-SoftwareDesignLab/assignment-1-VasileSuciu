package model;

import java.util.ArrayList;
import java.util.List;

public class Client {

    private Long id;
    private String idCardNumber;
    private String personalNumericalCode;
    private String address;
    private String name;

    public Long getId() { return id; }

    public String getIdCardNumber() { return idCardNumber; }

    public String getPersonalNumericalCode() { return personalNumericalCode; }

    public String getAddress() { return address; }

    public String getName() { return name; }

    public void setId(Long id) { this.id = id; }

    public void setIdCardNumber(String idCardNumber) { this.idCardNumber = idCardNumber; }

    public void setPersonalNumericalCode(String personalNumericalCode) { this.personalNumericalCode = personalNumericalCode; }

    public void setAddress(String address) { this.address = address; }

    public void setName(String name) { this.name = name; }

}
