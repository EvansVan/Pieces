package com.example.pieces;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.ViewHolder> {

	private Context context;
	ArrayList<Anime> anime;

	//constructor
	public AnimeAdapter(Context context, ArrayList<Anime> anime){
		this.context = this.context;
		this.anime = anime;
	}

	//class viewholder to initialize layout
	public class ViewHolder extends RecyclerView.ViewHolder {
		ImageView image;
		TextView title, synopsis, rating;

		public ViewHolder(@NonNull View itemView){
			super(itemView);
			image = itemView.findViewById(R.id.piecePic);
			title = itemView.findViewById(R.id.title);
			synopsis = itemView.findViewById(R.id.synopsis);
			rating = itemView.findViewById(R.id.rating);
		}
	}


	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		Anime r = anime.get(position);
		String image_url = r.getImage_uri();
		holder.title.setText(r.getTitle());
		holder.synopsis.setText(r.getSynopsis());
		holder.rating.setText(r.getRating());
		Picasso.get().load(image_url).fit().centerInside().into(holder.image);
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview,parent,false);
			return new ViewHolder(view);
	}

	@Override
	public int getItemCount(){
		return anime.size();
	}
}




