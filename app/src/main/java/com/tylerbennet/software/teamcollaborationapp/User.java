package com.tylerbennet.software.teamcollaborationapp;

/**
 * Created by Tyler Bennet on 3/10/2017.
 */

public class User {
    private String username;
    private String firstName;
    private String lastName;
    private String UUID;
    private String cellNumber;
    private String manages;
    private String onCall;
    private String customerID;
    private String address;

    public User() {

    }

    public User(String usernameIn, String firstNameIn, String lastNameIn,
                String UUIDIn, String cellNumIn, String managesIn,
                String onCallIn, String customerIDIn, String addressIn) {
        this.username = usernameIn;
        this.firstName = firstNameIn;
        this.lastName = lastNameIn;
        this.UUID = UUIDIn;
        this.cellNumber = cellNumIn;
        this.manages = managesIn;
        this.onCall = onCallIn;
        this.customerID = customerIDIn;
        this.address = addressIn;
    }

    public String getCellNumber() {
        return this.cellNumber;
    }

    public void setCellNumber(String inCellNum) {
        this.cellNumber = inCellNum;
    }

}
