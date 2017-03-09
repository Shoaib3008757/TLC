package com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Insurance;

import java.util.ArrayList;

/**
 * Created by Hafiz Adeel on 2/3/2017.
 */

public class ListofVehicleText {
    private String ListofVehicleId;
    private String PlateNumber;
    private String VinNumber;
    private String PolicyNumber;

    public ListofVehicleText(String listofVehicleId ,String plateNumber, String vinNumber, String policyNumber) {
        ListofVehicleId = listofVehicleId;
        PlateNumber = plateNumber;
        VinNumber = vinNumber;
        PolicyNumber = policyNumber;
    }

    public String getListofVehicleId() {
        return ListofVehicleId;
    }

    public String getPlateNumber() {
        return PlateNumber;
    }

    public String getVinNumber() {
        return VinNumber;
    }

    public String getPolicyNumber() {
        return PolicyNumber;
    }

    public void setPlateNumber(String plateNumber) {
        PlateNumber = plateNumber;
    }

    public void setListofVehicleId(String listofVehicleId) {
        ListofVehicleId = listofVehicleId;
    }

    public void setVinNumber(String vinNumber) {
        VinNumber = vinNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        PolicyNumber = policyNumber;
    }
}
