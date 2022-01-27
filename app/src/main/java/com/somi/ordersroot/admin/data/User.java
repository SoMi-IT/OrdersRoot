package com.somi.ordersroot.admin.data;

public class User {

    public User(String userId, String deviceId, String pinCode) {
        this.userId = userId;
        this.deviceId = deviceId;
        this.pinCode = pinCode;
    }

    private String userId;
    private String deviceId;
    private String pinCode;

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPinCode() {
        return pinCode;
    }
    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }



}
