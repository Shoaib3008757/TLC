package com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Summons;

/**
 * Created by Hafiz Adeel on 2/23/2017.
 */

public class ScheduleText {
    private String Scheduleid;
    private String HearingLocation;
    private String HearingDate;
    private String Contectno;
    private String Attorneyrep;
    private String Remarks;
    private String Compliance;

    public ScheduleText(String scheduleid, String hearingLocation, String hearingDate, String contectno, String attorneyrep, String remarks, String compliance) {
        Scheduleid = scheduleid;
        HearingLocation = hearingLocation;
        HearingDate = hearingDate;
        Contectno = contectno;
        Attorneyrep = attorneyrep;
        Remarks = remarks;
        Compliance = compliance;
    }

    public String getScheduleid() {
        return Scheduleid;
    }

    public String getHearingLocation() {
        return HearingLocation;
    }

    public String getHearingDate() {
        return HearingDate;
    }

    public String getContectno() {
        return Contectno;
    }

    public String getAttorneyrep() {
        return Attorneyrep;
    }

    public String getRemarks() {
        return Remarks;
    }

    public String getCompliance() {
        return Compliance;
    }

    public void setScheduleid(String scheduleid) {
        Scheduleid = scheduleid;
    }

    public void setHearingLocation(String hearingLocation) {
        HearingLocation = hearingLocation;
    }

    public void setHearingDate(String hearingDate) {
        HearingDate = hearingDate;
    }

    public void setContectno(String contectno) {
        Contectno = contectno;
    }

    public void setAttorneyrep(String attorneyrep) {
        Attorneyrep = attorneyrep;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public void setCompliance(String compliance) {
        Compliance = compliance;
    }
}
