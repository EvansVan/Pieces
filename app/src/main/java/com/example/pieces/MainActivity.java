package com.example.pieces;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

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
	private AnimeAdapter adapter;
	private ArrayList<anime> AnimeList;
	Context context;
	Toolbar toolbar;

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

		toolbar = findViewById(R.id.appbar);
		setSupportActionBar(toolbar);

		animeRecycler =findViewById(R.id.animeView);
		animeRecycler.setHasFixedSize(true);
		animeRecycler.setLayoutManager(new LinearLayoutManager(this));
		context = this;

		AnimeList = new ArrayList<>();

		mAuth = FirebaseAuth.getInstance();

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

										AnimeList.add(new anime(title,synopsis,imageUrl,score));
									}
									adapter = new AnimeAdapter(MainActivity.this,AnimeList);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu,menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch(item.getItemId()){
			case R.id.search:
				Toast.makeText(context,"title",Toast.LENGTH_SHORT).show();
			case R. id.signout:
				startActivity(new Intent(context,loginActivity.class));
		}
		return super.onOptionsItemSelected(item);
	}
}
