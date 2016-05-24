package com.example.admin.angpangii.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.admin.angpangii.Adapters.AlbumAdapter;
import com.example.admin.angpangii.Adapters.User;
import com.example.admin.angpangii.R;
import com.example.admin.angpangii.utils.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 4/26/2016.
 */
public class AlbumActivity extends AppCompatActivity {
    private static final String TAG = AlbumActivity.class.getSimpleName();
    private String url,urlImg;
    private ProgressDialog pDialog;
    private AlbumAdapter AlbAdapter;
    private int classId,userId;
    private Context context;
    private static String [] albumName;
    private static String [] imagesLink;
    private static int [] IdClass;
    private int count = 0;
    private int Class_ID;
    private GridView albums;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        context = getApplicationContext();

        getSupportActionBar().setTitle("Album");
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        albums = (GridView) findViewById(R.id.gridA);

        userId = User.getUser().getId();
        url  = "http://10.0.3.2:8000/v1/get/classes_of_user/" + userId ;
        Log.i("url: ",url);
        new ProcessClassJSON().execute(url);

        albums.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                classId = IdClass[position];
                /* Create an Intent that will start the Status-Activity. */
                Intent mainIntent = new Intent(AlbumActivity.this, SubAlbumActivity.class);
                mainIntent.putExtra("Pass_classId", classId);
                startActivity(mainIntent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    /**
     * Method to hide Progress Dialog
     * */
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    /**
     * Method to read JSON array
     *
     * */
    private class ProcessClassJSON extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(AlbumActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... urls){
            url = urls[0];
            JsonObjectRequest albumReq = new JsonObjectRequest(Request.Method.GET, url, (String)null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            hidePDialog();

                            // Parsing json
                            try {
                                JSONArray ja = response.getJSONArray("classes");
                                IdClass = new int[ja.length()];
                                albumName = new String[ja.length()];
                                imagesLink = new String[ja.length()];

                                for (int i = 0; i < ja.length(); i++) {

                                    JSONObject obj = ja.getJSONObject(i);
                                    int class_Id = obj.getInt("id");
                                    String class_name = obj.getString("class_name");
                                    albumName[i] = "Class " + class_Id;
                                    IdClass[i] = class_Id;
                                    Log.i("Album List Id: ", " " + IdClass[i]);
                                }

                                for (int i=0; i< albumName.length; i++){
                                    urlImg  = "http://10.0.3.2:8000/v1/get/album/" + IdClass[i];
                                    new ProcessImageJSON().execute(urlImg);
                                }
                            }catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }
                    },
                    new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    hidePDialog();
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

            AppController.getInstance().addToRequestQueue(albumReq);
            return null;
        }
    }

    /**
     * Method to read JSON array
     *
     * */
    private class ProcessImageJSON extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
        }

        @Override
        protected Void doInBackground(String... urls){
            url = urls[0];
            JsonObjectRequest albumReq = new JsonObjectRequest(Request.Method.GET, url, (String)null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            // Parsing json
                            try {
                                JSONArray ja = response.getJSONArray("pictures");

                                for (int i = 0; i < ja.length(); i++) {
                                    JSONObject obj = ja.getJSONObject(i);
                                    int pic_Id = obj.getInt("id");
                                    String pic_url = obj.getString("url");
                                    imagesLink[count] = "http://10.0.3.2:8000/v1/get/picture_of_class/"+ IdClass[count] + "/" + pic_url;
                                    Log.i("Album List url: ", " " + imagesLink[count]);
                                    count++;
                                    break;
                                }
                            }catch (JSONException e) {
                                e.printStackTrace();

                            }

                            AlbAdapter = new AlbumAdapter(context, albumName, imagesLink);
                            albums.setAdapter(AlbAdapter);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                            hidePDialog();
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

            AppController.getInstance().addToRequestQueue(albumReq);
            return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
