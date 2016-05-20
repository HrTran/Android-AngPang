package com.example.admin.angpangii.Items;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by li on 5/20/2016.
 */
public class User {
    private String basicAuth;
    private int id;
    private int type;
    private static User user = new User();

    private User(){};

    public void initUser(String basicAuth, JSONObject jsonObject, Context context) {
        this.basicAuth = basicAuth;
        try {
            this.id = jsonObject.getInt("id");
            this.type = jsonObject.getInt("type");
            Log.d("userid", String.valueOf(this.id));
            Log.d("usertype", String.valueOf(this.type));
        } catch (Exception e) {
            Toast.makeText(context, "Parsing json error", Toast.LENGTH_LONG);
        }
    }

    public static User getUser() {
        return user;
    }
}
