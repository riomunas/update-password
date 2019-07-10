/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssg.update.password;

/**
 *
 * @author rio
 */
public class IcMasUser {
    private String sid;
    private String userID;

    public IcMasUser(){}
    
    public IcMasUser(String sid, String userID) {
        this.sid = sid;
        this.userID = userID;
    }
    
    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    
    
}
