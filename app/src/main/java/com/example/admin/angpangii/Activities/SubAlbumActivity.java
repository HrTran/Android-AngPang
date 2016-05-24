/*******************************************************************************
 * Copyright 2011-2014 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.example.admin.angpangii.Activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.admin.angpangii.Adapters.SubAlbumAdapter;
import com.example.admin.angpangii.Adapters.User;
import com.example.admin.angpangii.utils.AppController;
import com.example.admin.angpangii.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class SubAlbumActivity extends AppCompatActivity {
	private static final String TAG = SubAlbumActivity.class.getSimpleName();
	private ProgressDialog pDialog;
	private GridView listView;
	private int classId;
	private static String [] imagesLink;
	private String url,urlImg;
	@Override
	public void onCreate( Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subalbum);
		getSupportActionBar().setTitle("Back to Album");
		//set the back arrow in the toolbar
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			classId = extras.getInt("Pass_classId");
		}
		listView = (GridView) findViewById(R.id.gridSA);
		String name = "Class " + classId;
		TextView albumName = (TextView) findViewById(R.id.txt_albumName);
		albumName.setText(name);
		urlImg  = "http://10.0.3.2:8000/v1/get/album/" + classId;
		new ProcessImageJSON().execute(urlImg);

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
								imagesLink = new String[ja.length()];
								for (int i = 0; i < ja.length(); i++) {
									JSONObject obj = ja.getJSONObject(i);
									int pic_Id = obj.getInt("id");
									String pic_url = obj.getString("url");
									imagesLink[i] = "http://10.0.3.2:8000/v1/get/picture_of_class/"+ classId + "/" + pic_url;
								}
							}catch (JSONException e) {
								e.printStackTrace();

							}
							 listView.setAdapter(new SubAlbumAdapter(SubAlbumActivity.this,imagesLink));
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

}