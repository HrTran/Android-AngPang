package com.example.admin.angpangii.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.angpangii.Items.User;
import com.example.admin.angpangii.R;
import com.example.admin.angpangii.utils.ConnectionInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;


public class CreatePost extends Activity {

    //ImageView avatarImageView;
    private EditText statusEditText;
    private TextView postTextView;
    private Spinner classSpinner;
    private ImageView avatar;
    private ImageView imageToUpload;
    private Button delPictureButton;
    private Button postButton;
    private Bitmap image;
    private Button bSelectImage;

    private Context context;
    private JSONObject jsonObject;
    private String name;
    private User user = User.getUser();
    private Map<Integer, String> classes = new HashMap<Integer, String>();
    private String myUrl = "http://10.0.3.2:8000/images/store";
    private RequestQueue queue;
    private int classId[];
    private String className[];
    private int classSelected = -1;
    private int changedPicture = 0;
    private ArrayAdapter<String> classList;
    private static final int RESULT_LOAD_IMAGE = 9002;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        queue = Volley.newRequestQueue(this);

        context = getApplicationContext();
        avatar = (ImageView) findViewById(R.id.avatarImageView);
        statusEditText = (EditText) findViewById(R.id.statusEditText);
        postTextView = (TextView) findViewById(R.id.postTextView);
        classSpinner = (Spinner) findViewById(R.id.classSpinner);
        imageToUpload = (ImageView) findViewById(R.id.imageToUpload);
        delPictureButton = (Button) findViewById(R.id.delPictureButton);
        postButton = (Button) findViewById(R.id.postButton);

        // if user is parent, hide the imageView, spinner and the textView
        if(user.getType() == User.PARENT){
            classSpinner.setVisibility(View.GONE);
            postTextView.setVisibility(View.GONE);
            imageToUpload.setVisibility(View.GONE);
            delPictureButton.setVisibility(View.GONE);
        } else {
            // initialize the spinner
            initClassSpinner();
            imageToUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changedPicture = 1;
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                }
            });
             delPictureButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     classSelected = -1;
                     changedPicture = 0;
                     imageToUpload.setImageResource(R.drawable.add_photo);
                 }
             });
        }

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPost();
            }
        });


        /*
        bSelectImage = (Button) findViewById(R.id.selectImageToUpload);
        bUploadImage = (Button) findViewById(R.id.bUploadImage);
        uploadImageName = (EditText) findViewById(R.id.etUplaodNames);
        responseTView = (TextView) findViewById(R.id.reponseTView);

        bSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
            imageToUpload.setImageURI(null);
            imageToUpload.setImageURI(selectedImage);
        }
    }

    private void sendPost() {
        String status = statusEditText.getText().toString();
        if(status == null) {
            // if the status is empty, notify user
            Toast.makeText(context, "Error: status is empty", Toast.LENGTH_LONG);
            return;
        }

        if(changedPicture == 1){
            // if the picture is choose but no class is selected, notify user
            if(classSelected == -1) {
                Toast.makeText(context, "Error: a class must be selected", Toast.LENGTH_LONG);
                return;
            } else {
                sendPostWithPicture();
            }
        }else {
            sendPostWithoutPicture();
        }
    }

    private void sendPostWithPicture() {
        String url = ConnectionInfo.HOST + "/v1/post/post/" + User.getUser().getId();
        StringRequest postRequest = new StringRequest(Request.Method.POST,
                url,
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
    }

    private void sendPostWithoutPicture() {

    }

    private void parseJson(JSONObject response) {
        try {
            JSONArray classArray = response.getJSONArray("classes");
            int length = classArray.length();
            classId = new int[length];
            className = new String[length];
            for(int i = 0; i < length; i++) {
                JSONObject tmp = classArray.getJSONObject(i);
                classId[i] = tmp.getInt("id");
                className[i] = tmp.getString("class_name");
            }
        } catch (Exception e) {
            Log.d("parsing error", e.toString());
        }
    }


    private void setUpSpinner() {
        classList = new ArrayAdapter<String>(
                context,
                android.R.layout.simple_spinner_item,
                className
        );
        classList.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        classSpinner.setAdapter(classList);
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                classSelected = parent.getSelectedItemPosition();
                Log.d("id classSelected", String.valueOf(classId[classSelected]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                classSelected = -1;
            }
        });
    }


    private void initClassSpinner() {
        String url = ConnectionInfo.HOST + "/v1/get/classes_of_user/" + User.getUser().getId();
        Log.d("url", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("classes of user", response.toString());
                        parseJson(response);
                        setUpSpinner();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error: class of user", error.toString());
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
