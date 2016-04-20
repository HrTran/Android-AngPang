package com.example.admin.angpangii.test;

import java.util.Date;

/**
 * Created by Admin on 4/20/2016.
 */
public class Status {
    private String username;
    private String statusTime;
    private String statusText;
    private String statusImage;

    public Status(String username, String statusTime ,String statusText, String statusImage) {
        this.username= username;
        this.statusTime= statusTime;
        this.statusText= statusText;
        this.statusImage= statusImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(String statusTime) {
        this.statusTime = statusTime;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getStatusImage() {
        return statusImage;
    }

    public void setStatusImage(String statusImage) {
        this.statusImage = statusImage;
    }


}
