package com.example.android.movieslibrary.model;

/**
 * Created by antonioyee on 12/10/17.
 */

public class Movie {

	private String name;
	private Integer gender;
	public Movie(String name, Integer gender) {
		this.name = name;
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

}
