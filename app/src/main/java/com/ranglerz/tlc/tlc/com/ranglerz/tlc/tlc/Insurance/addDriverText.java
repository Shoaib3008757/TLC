package com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Insurance;

/**
 * Created by Hafiz Adeel on 2/8/2017.
 */

public class addDriverText {
    private String AddDriverID;
    private String DriverName;
    private String DriverLicense;
    private String TLCLicense;
    private String PlateNumber;
    private String PolicyNumber;
    private String BaseName;

    public addDriverText(String addDriverID ,String driverName, String driverLicense, String TLCLicense, String plateNumber, String policyNumber, String baseName) {
        AddDriverID = addDriverID;
        DriverName = driverName;
        DriverLicense = driverLicense;
        this.TLCLicense = TLCLicense;
        PlateNumber = plateNumber;
        PolicyNumber = policyNumber;
        BaseName = baseName;
    }

    public String getAddDriverID() {
        return AddDriverID;
    }

    public String getDriverName() {
        return DriverName;
    }

    public String getDriverLicense() {
        return DriverLicense;
    }

    public String getTLCLicense() {
        return TLCLicense;
    }

    public String getPlateNumber() {
        return PlateNumber;
    }

    public String getPolicyNumber() {
        return PolicyNumber;
    }

    public String getBaseName() {
        return BaseName;
    }

    public void setAddDriverID(String addDriverID) {
        AddDriverID = addDriverID;
    }

    public void setDriverName(String driverName) {
        DriverName = driverName;
    }

    public void setDriverLicense(String driverLicense) {
        DriverLicense = driverLicense;
    }

    public void setTLCLicense(String TLCLicense) {
        this.TLCLicense = TLCLicense;
    }

    public void setPlateNumber(String plateNumber) {
        PlateNumber = plateNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        PolicyNumber = policyNumber;
    }

    public void setBaseName(String baseName) {
        BaseName = baseName;
    }
}
