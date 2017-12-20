package com.lyrics.model;

import java.sql.Timestamp;
import java.util.List;

public class MoviesLatest {

	private int id;
	private String movieName;
	private Timestamp movieReleaseDate;
	private List<L_lyrics> lyricsForMovie;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public List<L_lyrics> getLyricsForMovie() {
		return lyricsForMovie;
	}

	public void setLyricsForMovie(List<L_lyrics> lyricsForMovie) {
		this.lyricsForMovie = lyricsForMovie;
	}
}
