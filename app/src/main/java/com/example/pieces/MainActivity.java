package com.example.pieces;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

	ListView listView;
	ImageView piecePic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView =findViewById(R.id.listView);
		piecePic = findViewById(R.id.piecePic);

		String url = "https://api.jikan.moe/v3/search/anime?q=onepiece&limit=5";

		RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

		JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
						new Response.Listener<JSONObject>() {
							@Override
							public void onResponse(JSONObject response) {
								try {
									ArrayList<HashMap<String, String>> animeList= new ArrayList<>();
									JSONArray jsonArry = response.getJSONArray("results");
									for(int i=0;i<jsonArry.length();i++){
										HashMap<String,String> anime = new HashMap<>();
										JSONObject obj = jsonArry.getJSONObject(i);
										anime.put("title",obj.getString("title"));
										anime.put("synopsis", obj.getString("synopsis"));
										anime.put("score",obj.getString("score"));
										anime.put("image_url",obj.getString("image_url"));
										animeList.add(anime);
									}
									ListAdapter adapter = new SimpleAdapter(MainActivity.this, animeList, R.layout.listview,new String[]{"title","synopsis","score","image_url"}, new int[]{R.id.title, R.id.synopsis, R.id.rating, R.id.piecePic});
									listView.setAdapter(adapter);
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
