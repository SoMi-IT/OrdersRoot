package com.somi.ordersroot.admin.user;

public class User {

    public User(String userId) { this.userId = userId; }

    private String userId;
    private String userName;
    private String pinCode;

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) { this.userName = userName; }

    public String getPinCode() {
        return pinCode;
    }
    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }


}
