package com.example.pieces;

//Anime class to create entries for new anime
public class anime {
	private String title, synopsis, imageUrl;
	private int score;

	public anime(String title, String synopsis, String imageUrl, int score) {
		this.title =title;
		this.synopsis =synopsis;
		this.score =score;
		this.imageUrl = imageUrl;
	}

	public String getTitle() {
		return title;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public int getScore() {
		return score;
	}
}