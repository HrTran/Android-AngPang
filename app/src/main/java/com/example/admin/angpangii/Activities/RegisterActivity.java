package com.example.admin.angpangii.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.angpangii.Items.User;
import com.example.admin.angpangii.R;
import com.example.admin.angpangii.utils.ConnectionInfo;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Activity {

    private static final int RESULT_LOAD_IMAGE = 9002;

    private EditText fname;
    private EditText lname;
    private EditText email;
    private EditText password;
    private EditText phone;
    private Button chooseAvatar;
    private TextView selectedAvatar;
    private EditText day;
    private EditText month;
    private EditText year;
    private EditText address;
    private Spinner gender;
    private Button back;
    private Button signUp;

    private Bitmap image = null;
    private RequestQueue queue;
    private Context context;
    private int selectedGender = 0;
    String sex;
    private static final int[] daysPerMonth =
            { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        context = getApplicationContext();
        queue = Volley.newRequestQueue(this);

        // find all the view
        fname = (EditText) findViewById(R.id.FirstName);
        lname = (EditText) findViewById(R.id.LastName);
        email = (EditText) findViewById(R.id.Email);
        password = (EditText) findViewById(R.id.Password);
        phone = (EditText) findViewById(R.id.Phone);
        chooseAvatar = (Button) findViewById(R.id.avatarButton);
        selectedAvatar = (TextView) findViewById(R.id.avatarTextView);
        day = (EditText) findViewById(R.id.Date);
        month = (EditText) findViewById(R.id.Month);
        year = (EditText) findViewById(R.id.Year);
        address = (EditText) findViewById(R.id.Address);
        gender = (Spinner) findViewById(R.id.Gender);
        back = (Button) findViewById(R.id.Back);
        signUp = (Button) findViewById(R.id.SignUp);

        setupGenderSpinner();

        chooseAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            try {
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            }catch (Exception e) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
            }
            String path = getRealPathFromURI(getApplicationContext(), selectedImage);
            String tokens[] = path.split("/");
            selectedAvatar.setText(tokens[tokens.length-1]);
        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void setupGenderSpinner() {
        String labels[] = {"Gender", "Male", "Female"};
        ArrayAdapter<String> genderList = new ArrayAdapter<String>(
                context,
                android.R.layout.simple_spinner_item,
                labels
        );
        genderList.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        gender.setAdapter(genderList);

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGender = parent.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public static boolean notEntered(EditText editText) {
        return editText.getText().toString().equals("");
    }

    public void showError() {
        Toast.makeText(context, "You must fill all the fields", Toast.LENGTH_LONG).show();
    }

    public long getNumber(EditText editText) {
        long number = -1;
        String sNumber = editText.getText().toString();
        try {
            number = Long.parseLong(sNumber);
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
        return number;
    }

    public void sendData() {
        String firstName;
        String lastname;
        String sEmail;
        long mPhone, mDay, mMonth, mYear;

        // validate data
        if(notEntered(fname) || notEntered(lname)
                || notEntered(email) || notEntered(password)
                || notEntered(phone) || notEntered(day)
                || notEntered(month) || notEntered(year)
                || notEntered(address) || selectedGender == 0){
            showError();
            return;
        }

        sEmail = email.getText().toString();

        if(!Login2Activity.isEmail(sEmail)) {
            Toast.makeText(context, "Entered email is incorrect", Toast.LENGTH_LONG).show();
            return;
        }

        mPhone = getNumber(phone);
        if(mPhone <= 0) {
            Toast.makeText(context, "Invalid phone", Toast.LENGTH_LONG).show();
            return;
        }

        mDay = getNumber(day);
        mMonth = getNumber(month);
        mYear = getNumber(year);
        if(!checkDay(mDay, mMonth, mYear)) {
            Toast.makeText(context, "Invalid birthday", Toast.LENGTH_LONG).show();
            return;
        }

        final String birth = year.getText().toString() + "-" + month.getText().toString() + "-" +
                day.getText().toString();

        if(selectedGender == 1) {
            sex = "1";
        }else {
            sex = "0";
        }

        // now send data
        String url = ConnectionInfo.HOST + "/v1/register";
        StringRequest postRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                        Log.d("Response", response);
                        if(response.equals("Account is created successfully")) {
                            // goto enter student token

                        };
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("fname", fname.getText().toString());
                params.put("lname", lname.getText().toString());
                params.put("email", email.getText().toString());
                params.put("birthday", birth);
                params.put("sex", sex);
                params.put("address", address.getText().toString());
                params.put("phone", phone.getText().toString());
                params.put("password", password.getText().toString());
                if(image == null) {
                    params.put("avatar", "null");
                }else {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    params.put("avatar", encodedImage);
                }
                return params;
            }
        };

        queue.add(postRequest);
    }

    private boolean checkDay(long mDay, long mMonth, long mYear) {
        if(mMonth < 1 || mMonth > 12) return false;
        if(mYear < 1900) return false;
        // check for leap year
        if (mMonth == 2 && mDay == 29 && (mYear % 400 == 0 || (mYear % 4 == 0 && mYear % 100 != 0)))
            return true;
        // check if day in range for month
        if (mDay > 0 && mDay <= daysPerMonth[(int)mMonth])
            return true;

        return false;
    } // end method checkDay
}
