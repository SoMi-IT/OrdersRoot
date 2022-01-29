package com.somi.ordersroot.admin.license;

public class License {

    public License(String licenseId) {
        this.licenseId = licenseId;
    }

    private String licenseId;
    private String deviceId;

    public String getLicenseId() {
        return licenseId;
    }
    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


}
