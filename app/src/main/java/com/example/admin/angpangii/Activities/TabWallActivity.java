package com.example.admin.angpangii.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.angpangii.Adapters.StatusAdapter;
import com.example.admin.angpangii.Adapters.Status;
import com.example.admin.angpangii.Adapters.User;
import com.example.admin.angpangii.R;
import com.example.admin.angpangii.utils.ConnectionInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 5/3/2016.
 */
public class TabWallActivity extends Fragment {
    List<Status> list;
    ListView listView;
    RequestQueue queue;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_wall, container, false);

        // Display status on Home Activity
        context = getActivity().getApplicationContext();
        queue = Volley.newRequestQueue(context);

        listView = (ListView) view.findViewById(R.id.listW);
        listView.setFastScrollEnabled(true);
        listView.setScrollingCacheEnabled(true);
        // When user click in items in listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                Status status = (Status) o;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                /* Create an Intent that will start the Status-Activity. */
                        Intent mainIntent = new Intent(getActivity(), StatusActivity.class);
                        startActivity(mainIntent);

                    }
                }, 0);

            }
        });

        getListData(0, 20);
        return view;
    }


    /**
     * Method to make data for listview
     */
    private void getListData(int start, int end) {
        String url = ConnectionInfo.HOST + "/v1/new_feeds/" + User.getUser().getId() + "/" + start + "/" + end;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        list = new ArrayList<Status>();
                        try {
                            JSONArray posts = response.getJSONArray("posts");
                            JSONObject post;
                            String picture;
                            String name;
                            String avatar;
                            Status status;
                            int length = posts.length();
                            for(int i = 0; i < length; i++) {
                                post = posts.getJSONObject(i);
                                name = post.getString("fname") + " " + post.getString("lname");
                                if(post.getString("avatar").equals("null")) {
                                    avatar = null;
                                }else {
                                    avatar = ConnectionInfo.HOST + "/v1/getavatar/" + post.getString("id_user");
                                }
                                if(post.getString("id_class").equals("null")) {
                                    picture = null;
                                }else{
                                    picture = ConnectionInfo.HOST + "/v1/get/picture_of_class/"
                                            + post.getString("id_class") + "/" + post.getString("url");
                                }

                                status = new Status(name, avatar, post.getString("created_at"),
                                        post.getString("status"), picture);
                                list.add(status);
                            }
                        } catch (Exception e) {
                            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                        }

                        listView.setAdapter(new StatusAdapter(getActivity(), list));

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error: class of user", error.toString());
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

        /*
        Status stt1 = new Status("Cô giáo Thảo", "f5", "21/04/2016 5:59p.m", "Một ngày đẹp trời. Thời tiết thật dễ chịu.", "hinhanh1");
        Status stt2 = new Status("Cô giáo Nga", "f5", "20/04/2016 4:55 p.m", "Càng lúc càng yêu nghề", "hinhanh2");
        Status stt3 = new Status("Cô giáo Thu", "f5", "19/04/2016 3:30 p.m", "Nhiều lúc chỉ muốn ngồi nhìn các con chơi", "hinhanh3");
        Status stt4 = new Status("Cô giáo Hà", "f5", "19/04/2016 10:01 a.m", "Đi đâu đó cũng nhớ về đây", "hinhanh1");
        Status stt5 = new Status("Cô giáo Thủy", "f5", "18/04/2016 9:23 a.m", "Chưa bao giờ cảm thấy yêu nghề thế này", "hinhanh2");
        Status stt6 = new Status("Cô giáo Thúy", "f5", "18/04/2016 8:36 a.m", "Mùa hè đến rồi!", "hinhanh3");
        Status stt7 = new Status("Cô giáo Tú", "f5", "18/04/2016 8:00 a.m", "", "hinhanh1");


        list.add(stt1);
        list.add(stt2);
        list.add(stt3);
        list.add(stt4);
        list.add(stt5);
        list.add(stt6);
        list.add(stt7);
        */
    }
}
