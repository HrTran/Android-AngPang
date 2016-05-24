package com.example.admin.angpangii.Activities;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.admin.angpangii.Adapters.User;
import com.example.admin.angpangii.R;
import com.example.admin.angpangii.utils.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 5/20/2016.
 */
public class SubHealthActivity extends HealthActivity {

    private static final String TAG = SubHealthActivity.class.getSimpleName();
    private String url;
    private String AvaUrl = "http://10.0.3.2:8000/v1/get/child_picture/3";
    private ProgressDialog pDialog;
    // temporary string to show the parsed response
    private String jsonResponse, jsonImage;
    private ImageView childAva;
    private TextView mood;
    private TextView health;
    private TextView temp;
    private TextView sleep;
    private TextView food;
    private TextView childName;
    private int childID;
    private TextView btnM,btnH,btnT,btnS,btnF;
    private EditText hidM,hidH,hidT,hidS,hidF;
    //private ImageLoader imageLoader;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_subhealth);
        getSupportActionBar().setTitle("Back to Notice");
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //get child id from health activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            childID = extras.getInt("Pass_childId");
        }
        childAva = (ImageView) findViewById(R.id.img_childAva);
        childName = (TextView) findViewById(R.id.txt_childName);
        mood = (TextView) findViewById(R.id.comM);
        health = (TextView) findViewById(R.id.comH);
        temp = (TextView) findViewById(R.id.comT);
        sleep = (TextView) findViewById(R.id.comS);
        food = (TextView) findViewById(R.id.comF);

        btnM = (TextView) findViewById(R.id.editM);
        btnH = (TextView) findViewById(R.id.editH);
        btnT = (TextView) findViewById(R.id.editT);
        btnS = (TextView) findViewById(R.id.editS);
        btnF = (TextView) findViewById(R.id.editF);

        hidM = (EditText) findViewById(R.id.hidden_comM);
        hidH = (EditText) findViewById(R.id.hidden_comH);
        hidT = (EditText) findViewById(R.id.hidden_comT);
        hidS = (EditText) findViewById(R.id.hidden_comS);
        hidF = (EditText) findViewById(R.id.hidden_comF);

        btnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickNext("M");hidM.setVisibility(View.VISIBLE);btnM.setVisibility(View.INVISIBLE);}
        });
        btnH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickNext("H");hidH.setVisibility(View.VISIBLE);btnH.setVisibility(View.INVISIBLE); }
        });
        btnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickNext("T");btnT.setVisibility(View.INVISIBLE);hidT.setVisibility(View.VISIBLE);}
        });
        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickNext("S");btnS.setVisibility(View.INVISIBLE);hidS.setVisibility(View.VISIBLE); }
        });
        btnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickNext("F");btnF.setVisibility(View.INVISIBLE);hidF.setVisibility(View.VISIBLE); }
        });

        hidM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickPrevious("M");hidM.setVisibility(View.INVISIBLE);btnM.setVisibility(View.VISIBLE); }
        });
        hidH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickPrevious("H");hidH.setVisibility(View.INVISIBLE);btnH.setVisibility(View.VISIBLE); }
        });
        hidT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickPrevious("T");hidT.setVisibility(View.INVISIBLE);btnT.setVisibility(View.VISIBLE); }
        });
        hidS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickPrevious("S");hidS.setVisibility(View.INVISIBLE);btnS.setVisibility(View.VISIBLE); }
        });
        hidF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickPrevious("F");hidF.setVisibility(View.INVISIBLE);btnF.setVisibility(View.VISIBLE); }
        });

        /*jsonImage = "http://10.0.3.2:8000/v1/get/child_picture/3";
        imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext()).getImageLoader();
        imageLoader.get(jsonImage,
                ImageLoader.getImageListener(childAva, R.drawable.f5, android.R.drawable.ic_dialog_alert));

        childAva.setImageUrl(jsonImage, imageLoader);*/
        // Retrieves an image specified by the URL, displays it in the UI.

        url = "http://10.0.3.2:8000/v1/get/child_info/" + childID;
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        ChildAvaRequest();
        makeJsonObjectRequest();
    }

    /*
    * This method's purpose is making edit at cell of table
    * @param label is label of each category mood(M), temp(T), v..v...
    * */
    public void onClickNext(String label) {
        // Change TextView to EditText with beginning of TextView's content
        String textSwitcher = "txt_switcher" + label;
        ViewSwitcher txtswitcher = (ViewSwitcher) findViewById(getResources().getIdentifier(textSwitcher, "id", getPackageName()));
        txtswitcher.showNext();
        String comment = "com" + label;
        TextView myText = (TextView) txtswitcher.findViewById(getResources().getIdentifier(comment, "id", getPackageName()));
        String hidComment = "hidden_com" + label;
        TextView myHidText = (TextView) txtswitcher.findViewById(getResources().getIdentifier(hidComment, "id", getPackageName()));
        myHidText.setText(myText.getText().toString());
    }

    /*
    * This method's purpose is comming back from edit
    * @param label is label of each category mood(M), temp(T), v..v...
    * */
    public void onClickPrevious(String label) {
        // Change TextView to EditText with beginning of TextView's content
        String textSwitcher = "txt_switcher" + label;
        ViewSwitcher txtswitcher = (ViewSwitcher) findViewById(getResources().getIdentifier(textSwitcher, "id", getPackageName()));
        txtswitcher.showNext();
        String comment = "com" + label;
        TextView myText = (TextView) txtswitcher.findViewById(getResources().getIdentifier(comment, "id", getPackageName()));
        String hidComment = "hidden_com" + label;
        TextView myHidText = (TextView) txtswitcher.findViewById(getResources().getIdentifier(hidComment, "id", getPackageName()));
        myText.setText(myHidText.getText().toString());

        /*// Change button from "Done" to "Edit"
        String buttonSwitcher = "btn_switcher" + label;
        ViewSwitcher btnswitcher = (ViewSwitcher) findViewById(getResources().getIdentifier(buttonSwitcher, "id", getPackageName()));
        btnswitcher.showNext();*/
    }

    /**
     * Method to load ava of children
     */
    public void ChildAvaRequest() {
        String url = "http://10.0.3.2:8000/v1/get/child_picture/3";
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        childAva.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.d("test image:", error.toString());
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
        AppController.getInstance().addToRequestQueue(request);

    }


    /**
     * Method to make json object request where json response starts wtih {
     * */
    private void makeJsonObjectRequest() {

        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, (String)null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    String id = response.getString("id");
                    String fname = response.getString("fname");
                    String mname = response.getString("mname");
                    String lname = response.getString("lname");
                    String birthday = response.getString("birthday");
                    String gender = response.getString("sex");
                    String address = response.getString("address");
                    String child_mood = response.getString("mood");
                    String child_health = response.getString("health");
                    String child_temp = response.getString("temperature");
                    String child_sleep = response.getString("sleep");
                    String child_food = response.getString("food");
                    String child_idClass = response.getString("id_class");

                    jsonResponse = "";
                    if (mname.equals("")){
                        jsonResponse += fname + " " + lname + "\0";
                    } else jsonResponse += fname + " " + mname + " " + lname + "\0";


                    childName.setText(jsonResponse);
                    mood.setText(child_mood);
                    health.setText(child_health);
                    temp.setText(child_temp);
                    sleep.setText(child_sleep);
                    food.setText(child_food);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
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

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}