package com.libraryapp.domain;


import java.util.List;


public class Users {
    private int userID;

    private String userName;
    private String userFullName;
    private String email;
    private String phone;
    private String password;
    private AccessLevel accessLevel;
    private List<Orders> myOrders;

    public Users() {
    }

    public Users(int userID, String userFullName, String email, String phone, String password, AccessLevel accessLevel, String userName) {
        this.userID = userID;
        this.userFullName = userFullName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.accessLevel = accessLevel;
        this.userName = userName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Orders> getMyOrders() {
        return myOrders;
    }

    public void setMyOrders(List<Orders> myOrders) {
        this.myOrders = myOrders;
    }
}
