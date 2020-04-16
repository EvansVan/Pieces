package com.example.pieces;

public class Anime {
	String title, image_uri, synopsis;
	float rating;

	public Anime(String title, String synopsis, int score, String imageUrl){
	}

	public Anime(String title, String image_uri, String synopsis, float rating) {
		this.title = title;
		this.image_uri = image_uri;
		this.synopsis = synopsis;
		this.rating = rating;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage_uri() {
		return image_uri;
	}

	public void setImage_uri(String image_uri) {
		this.image_uri = image_uri;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public int getRating() {
		return (int) rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}
}
