package com.example.admin.angpangii.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.angpangii.Items.User;
import com.example.admin.angpangii.R;
import com.example.admin.angpangii.utils.ConnectionInfo;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddChildrenActivity extends Activity {

    private EditText token;
    private Button send;
    private TextView result;
    private Button add;
    private Button done;

    private Context context;
    private int child_id = -1;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_children);

        context = getApplicationContext();
        queue = Volley.newRequestQueue(context);

        token = (EditText) findViewById(R.id.token);
        send = (Button) findViewById(R.id.send);
        result = (TextView) findViewById(R.id.result);
        done = (Button) findViewById(R.id.done);
        add = (Button) findViewById(R.id.add);
        add.setVisibility(View.INVISIBLE);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToken();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addChild();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddChildrenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void sendToken() {
        String sToken = token.getText().toString();
        if(sToken.equals("")) {
            Toast.makeText(context, "You must supply token", Toast.LENGTH_LONG).show();
            return;
        }
        String url = ConnectionInfo.HOST + "/v1/get/child_by_token/" + sToken;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            child_id = response.getInt("id");
                            String fname = response.getString("fname");
                            String lname = response.getString("lname");
                            result.setText(fname + " " + lname);
                            add.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            Toast.makeText(context, "Not matched any token", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap< String, String >();
                headers.put("Authorization", User.getUser().getBasicAuth());
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };

        queue.add(jsObjRequest);
    }


    private void addChild() {
        if(child_id == -1) {
            Toast.makeText(context, "Enter another token first", Toast.LENGTH_LONG).show();
            return;
        }
        String url = ConnectionInfo.HOST + "/v1/add_child/" + User.getUser().getId() + "/" + child_id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap< String, String >();
                headers.put("Authorization", User.getUser().getBasicAuth());
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };

        queue.add(stringRequest);    }

}
