package com.example.admin.angpangii.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.admin.angpangii.Items.User;
import com.example.admin.angpangii.R;
import com.example.admin.angpangii.utils.ConnectionInfo;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;


public class CreatePost extends Activity {

    //ImageView avatarImageView;
    EditText statusEditText;
    Spinner classSpinner;
    ImageView imageToUplaod;
    Button postButton;
    Bitmap image;
    Button bSelectImage;
    Context context;
    String name;
    String myUrl = "http://10.0.3.2:8000/images/store";
    RequestQueue queue;
    private static final int RESULT_LOAD_IMAGE = 9002;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        statusEditText = (EditText) findViewById(R.id.statusEditText);
        classSpinner = (Spinner) findViewById(R.id.classSpinner);
        /*
        imageToUplaod = (ImageView) findViewById(R.id.imageToUplaod);
        bSelectImage = (Button) findViewById(R.id.selectImageToUpload);
        bUploadImage = (Button) findViewById(R.id.bUploadImage);
        uploadImageName = (EditText) findViewById(R.id.etUplaodNames);
        responseTView = (TextView) findViewById(R.id.reponseTView);
        context = getApplicationContext();
        queue = Volley.newRequestQueue(this);

        bSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        bUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image = ((BitmapDrawable) imageToUplaod.getDrawable()).getBitmap();
                name = uploadImageName.getText().toString();
                // checking network connection
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    makeRequest();
                } else {
                    Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG);
                }
            }
        });
        */
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            imageToUplaod.setImageURI(null);
            imageToUplaod.setImageURI(selectedImage);
            bSelectImage.setText("Change Image");
        }
    }

    private void makeRequest(){
        StringRequest postRequest = new StringRequest(Request.Method.POST,
                ConnectionInfo.HOST + "/images/store",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap < String, String > ();
                headers.put("Authorization", User.getUser().getBasicAuth());
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("name","luong");
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                params.put("image", encodedImage);
                return params;
            }

        };
        queue.add(postRequest);
    }

}
