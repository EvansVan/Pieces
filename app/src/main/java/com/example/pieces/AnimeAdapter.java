package com.example.pieces;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder> {

		private ArrayList<anime> AnimeList;

		public AnimeAdapter (Context context, ArrayList<anime> animeList){
			this.AnimeList = animeList;
	}

	@Override
	public AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview, parent, false);
		return new AnimeViewHolder(v);
	}

	@Override
	public void onBindViewHolder(@NonNull AnimeViewHolder holder, int position) {
		anime currentItem = AnimeList.get(position);
		
		holder.title.setText(currentItem.getTitle());
		holder.synopsis.setText(currentItem.getSynopsis());
		holder.score.setText(String.valueOf(currentItem.getScore()));
		Picasso.get().load(currentItem.getImageUrl()).fit().centerInside().into(holder.animePic);
	}

	@Override
	public int getItemCount() {
		return AnimeList.size();
	}

	class AnimeViewHolder extends RecyclerView.ViewHolder{
			ImageView animePic;
			TextView title, synopsis, score;

			public AnimeViewHolder(@NonNull View itemView) {
				super(itemView);
				animePic = itemView.findViewById(R.id.piecePic);
				title = itemView.findViewById(R.id.title);
				synopsis = itemView.findViewById(R.id.synopsis);
				score = itemView.findViewById(R.id.score);
			}
		}
}
