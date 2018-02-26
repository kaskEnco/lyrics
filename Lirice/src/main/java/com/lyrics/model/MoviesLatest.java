package com.lyrics.model;

import java.io.Serializable;
import java.sql.Timestamp;
//import java.util.List;

public class MoviesLatest implements Serializable {

	private int movieId;
	private String movieName;
	private Timestamp movieReleaseDate;
	

	

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public Timestamp getMovieReleaseDate() {
		return movieReleaseDate;
	}

	public void setMovieReleaseDate(Timestamp movieReleaseDate) {
		this.movieReleaseDate = movieReleaseDate;
	}

	
}
