package com.example.admin.angpangii.Activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.example.admin.angpangii.Adapters.AlbumAdapter;
import com.example.admin.angpangii.Adapters.AlbumItems;
import com.example.admin.angpangii.Adapters.Status;
import com.example.admin.angpangii.Adapters.User;
import com.example.admin.angpangii.R;
import com.example.admin.angpangii.utils.AppController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 4/26/2016.
 */
public class AlbumActivity extends AppCompatActivity {
    private static final String TAG = AlbumActivity.class.getSimpleName();
    //private String url ;
    private ImageView img1,img2,img3;
    private ImageLoader imageLoader;
    RequestQueue queue;
    public static String [] albumNameList={"Class 1","Class 2","Class 3","Class 4","Class 5",};
    public static int [] albumImages={R.drawable.anh1,R.drawable.anh2,R.drawable.anh3,R.drawable.anh4,R.drawable.anh5};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        getSupportActionBar().setTitle("Album");
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GridView albums = (GridView) findViewById(R.id.gridA);
        albums.setAdapter(new AlbumAdapter(this,albumNameList,albumImages));

        /*url="http://10.0.3.2:8000/v1/getavatar/1";
        imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext()).getImageLoader();
        imageLoader.get(url, ImageLoader.getImageListener(img1, R.drawable.anh1, android.R.drawable.ic_dialog_alert));
        img2.setImageUrl(url, imageLoader);*/

        // Load Image from server
        String url = "http://10.0.3.2:8000/v1/getavatar/1";
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        img1.setImageBitmap(bitmap);
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
        //queue.add(request);

    }

    /**
     * Method to make data for listview
     */
    private List<AlbumItems> getListData() {
        List<AlbumItems> list = new ArrayList<AlbumItems>();
        AlbumItems stt1 = new AlbumItems("Cô giáo Thảo", "f5");
        AlbumItems stt2 = new AlbumItems("Cô giáo Nga", "f5");
        AlbumItems stt3 = new AlbumItems("Cô giáo Thu", "f5");
        AlbumItems stt4 = new AlbumItems("Cô giáo Hà", "f5");
        AlbumItems stt5 = new AlbumItems("Cô giáo Thủy", "f5");
        AlbumItems stt6 = new AlbumItems("Cô giáo Thúy", "f5");
        AlbumItems stt7 = new AlbumItems("Cô giáo Tú", "f5");

        list.add(stt1);
        list.add(stt2);
        list.add(stt3);
        list.add(stt4);
        list.add(stt5);
        list.add(stt6);
        list.add(stt7);

        return list;
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
