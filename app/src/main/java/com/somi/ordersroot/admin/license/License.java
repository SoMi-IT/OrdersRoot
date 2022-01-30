package com.somi.ordersroot.admin.license;

public class License {

    public License(String licenseId) {
        this.licenseId = licenseId;
    }

    private String licenseId;
    private String licenseName;
    private String deviceId;

    public String getLicenseId() {
        return licenseId;
    }
    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getLicenseName() {
        return licenseName;
    }
    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }

    public String getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


}
