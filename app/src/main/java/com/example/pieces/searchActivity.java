package com.example.pieces;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class searchActivity extends AppCompatActivity {

	Context context;
	TextView responseView;
	Button search;
	EditText emailText;
	ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

		context = this;
		responseView = findViewById(R.id.responseView);
		emailText = findViewById(R.id.animeText);
		progressBar = findViewById(R.id.progressBar);
		search = findViewById(R.id.search);
		final String api_url = "https://api.jikan.moe/v3/search/";

		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Url containing json information to be displayed
				String anime = emailText.getText().toString();
				String url = api_url + "anime?q=" + anime ;

				//volley request to get data and populate anime list
				RequestQueue queue = Volley.newRequestQueue(context);
				JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
								new Response.Listener<JSONObject>() {
									@Override
									public void onResponse(JSONObject response) {
										try {
											JSONArray jsonArray = response.getJSONArray("results");
											String val = jsonArray.toString();
											responseView.setText(val);
										}
										catch (JSONException ex){
											Log.e("JsonParser Example","unexpected JSON exception", ex);
										}
									}
								},
								new Response.ErrorListener() {
									@Override
									public void onErrorResponse(VolleyError error) {
										error.printStackTrace();
									}
								});
				queue.add(request);
			}
		});
	}
}
