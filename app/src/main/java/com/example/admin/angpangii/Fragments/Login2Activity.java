package com.example.admin.angpangii.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.angpangii.Items.User;
import com.example.admin.angpangii.R;
import com.example.admin.angpangii.utils.ConnectionInfo;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Login2Activity extends AppCompatActivity {

    private String userName;
    private String password;
    private String basicAuth = null;
    private boolean remember = true;
    private Toast toast;
    private Context context;
    private JSONObject jsonObject;

    private EditText emailText;
    private EditText passwordText;
    private CheckBox rememberCheckBox;
    private Button signInButton;
    private TextView createAccountTView;
    private TextView resultTView;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        context = getApplicationContext();

        // get view
        emailText = (EditText) findViewById(R.id.emailText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        rememberCheckBox = (CheckBox) findViewById(R.id.rememberCheckBox);
        signInButton = (Button) findViewById(R.id.signInButton);
        resultTView = (TextView) findViewById(R.id.resultTView);
        createAccountTView = (TextView) findViewById(R.id.createAccoutTView);
        queue = Volley.newRequestQueue(this);

        // handle remember checkbox
        rememberCheckBox.setChecked(true);
        rememberCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    remember = true;
                }else{
                    remember = false;
                }
            }
        });

        // handle sign in button
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = emailText.getText().toString();
                password = passwordText.getText().toString();

                if(isEmail(userName)){
                    ConnectivityManager connMgr = (ConnectivityManager)
                            getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        // if is connected to network
                        new Communicator().execute(ConnectionInfo.HOST + "/v1/login");
                    } else {
                        // if not, display a warning
                        toast = Toast.makeText(context, "No network connection available.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }else{
                    toast = Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    public void getUserInfo (){

        final JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,
                ConnectionInfo.HOST + "/v1/get/user_detail/" + userName,
                null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        resultTView.setText("Response: " + response.toString());
                        jsonObject =  response;
                        User.getUser().initUser(basicAuth, jsonObject, context);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        resultTView.setText(error.toString());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap< String, String >();
                headers.put("Authorization", basicAuth);
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };

        queue.add(jsObjRequest);
    }

    public void goToMainScreen() {
        Toast.makeText(context, "goto", Toast.LENGTH_LONG);
        Intent intent = new Intent(Login2Activity.this, MainActivity.class);
        Login2Activity.this.startActivity(intent);
        Login2Activity.this.finish();
    }

    public boolean isEmail(String email) {
        if(email.indexOf(' ') != -1){
            // if email has space, return false
            return false;
        }
        if(email.indexOf('.') == -1){
            // if email don't have . character, return false
            return false;
        }
        if(email.indexOf('@') == -1){
            // if email don't have @ character, return false
            return false;
        }
        return true;
    }

    public String downloadUrl(String myUrl) throws IOException {
        InputStream is = null;
        String userCredential = userName + ":" + password;
        basicAuth = "Basic " + new String(
                Base64.encodeToString(userCredential.getBytes(),
                Base64.DEFAULT));
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestProperty ("Authorization", basicAuth);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    private class Communicator extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "0";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            char c = result.charAt(0);
            if(c == '1'){
                resultTView.setText("\"" + c + "\"");
                getUserInfo();
                //goToMainScreen();
            }else{
                basicAuth = null;
                resultTView.setText("\"" + result + "\"" + c);
            }
        }
    }

}
