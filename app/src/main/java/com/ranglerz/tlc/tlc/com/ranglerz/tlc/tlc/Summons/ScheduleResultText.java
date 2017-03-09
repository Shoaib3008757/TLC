package com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Summons;

/**
 * Created by Hafiz Adeel on 2/21/2017.
 */

public class ScheduleResultText {

    private String SummonType;
    private String SummonNumber;
    private String SummonData;
    private String HearingLocation;
    private String TlcLicense;
    private String PlateMedallion;
    private String SummonTableId;



    public ScheduleResultText(String summonType ,String summonNumber, String summonData, String hearingLocation, String tlcLicense, String plateMedallion , String summomTableid) {
        SummonNumber = summonNumber;
        SummonData = summonData;
        HearingLocation = hearingLocation;
        TlcLicense = tlcLicense;
        PlateMedallion = plateMedallion;
        SummonType = summonType;
        SummonTableId = summomTableid;

    }

    public String getSummonTableId() {
        return SummonTableId;
    }

    public String getSummonType() {
        return SummonType;
    }

    public String getSummonNumber() {
        return SummonNumber;
    }

    public String getSummonData() {
        return SummonData;
    }

    public String getHearingLocation() {
        return HearingLocation;
    }

    public String getTlcLicense() {
        return TlcLicense;
    }

    public String getPlateMedallion() {
        return PlateMedallion;
    }

    public void setSummonNumber(String summonNumber) {
        SummonNumber = summonNumber;
    }

    public void setSummonData(String summonData) {
        SummonData = summonData;
    }

    public void setHearingLocation(String hearingLocation) {
        HearingLocation = hearingLocation;
    }

    public void setTlcLicense(String tlcLicense) {
        TlcLicense = tlcLicense;
    }

    public void setPlateMedallion(String plateMedallion) {
        PlateMedallion = plateMedallion;
    }

    public void setSummonType(String summonType) {
        SummonType = summonType;
    }

    public void setSummonTableId(String summonTableId) {
        SummonTableId = summonTableId;
    }
}
