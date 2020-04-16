package com.example.pieces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	private FirebaseAuth mAuth;
	RecyclerView animeRecycler;
	ArrayList<Anime> animeList;
	AnimeAdapter adapter;
	Context context;

	@Override
	protected void onStart() {
		super.onStart();
		checkAuth();
	}

	private void checkAuth() {
		FirebaseUser currentuser = mAuth.getCurrentUser();

		if (currentuser == null){
			startActivity(new Intent(context,loginActivity.class));
		}
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		animeRecycler =findViewById(R.id.animeView);
		animeRecycler.setHasFixedSize(true);
		animeRecycler.setLayoutManager(new LinearLayoutManager(this));
		context = this;

		mAuth = FirebaseAuth.getInstance();

		animeList = new ArrayList<Anime>();

		String url = "https://api.jikan.moe/v3/search/anime?q=onepiece&limit=5";

		RequestQueue queue = Volley.newRequestQueue(context);

		JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
						new Response.Listener<JSONObject>() {
							@Override
							public void onResponse(JSONObject response) {
								try {

									JSONArray jsonArray = response.getJSONArray("results");
									for(int i=0;i<jsonArray.length();i++){
										JSONObject obj = jsonArray.getJSONObject(i);

										String title = obj.getString("title");
										String synopsis = obj.getString("synopsis");
										int score = obj.getInt("score");
										String imageUrl = obj.getString("image_url");

										animeList.add(new Anime(title,synopsis,score,imageUrl));
									}
									adapter = new AnimeAdapter(context,animeList);
									animeRecycler.setAdapter(adapter);
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
}
