package com.example.admin.angpangii.utils;

import android.util.Base64;

/**
 * Created by li on 5/19/2016.
 */
public class ConnectionInfo {
    public static final String HOST = "http://10.0.3.2:8000";
    private String credentials = "luong@luong.com" + ":" + "1";
    public static String au = "1";

    private String auth = "Basic "
            + Base64.encodeToString(credentials.getBytes(),
            Base64.NO_WRAP);

    private static final ConnectionInfo connectionInfo = new ConnectionInfo();

    private ConnectionInfo(){};

    public static ConnectionInfo getInstance() {
        return connectionInfo;
    }

    public String getAuth() {
        return auth;
    }

}
