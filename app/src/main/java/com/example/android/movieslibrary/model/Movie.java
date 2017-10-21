package com.example.android.movieslibrary.model;

/**
 * Created by antonioyee on 12/10/17.
 */

public class Movie {

	private String name;
	private Integer genre;
	private Integer rating;

	public Movie(String name, Integer genre, Integer rating) {
		this.name = name;
		this.genre = genre;
		this.rating = rating;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGenre() {
		return genre;
	}

	public void setGenre(Integer genre) {
		this.genre = genre;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}
}
