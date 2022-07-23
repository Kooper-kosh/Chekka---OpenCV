package com.chekka.capstone;

/**
 * Created by User on 1/21/2017.
 */

public class XYValue {

    private String sn;
    private String email;
    private String sid;


    public XYValue(String sn, String email, String sid) {
        this.sn = sn;
        this.email = email;
        this.sid = sid;
    }

    public String getSN() {
        return sn;
    }

    public String getEmail() {
        return email;
    }
    public String getSid() {
        return sid;
    }
}
