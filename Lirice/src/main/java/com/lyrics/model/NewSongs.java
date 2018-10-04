package com.lyrics.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class NewSongs implements Serializable {

	private int id;
	private String lyricname;
	private String moviename;
	private int movieId;
	private String writerName;
	private Timestamp movieReleaseDate;
	
	
	public String getLyricname() {
		return lyricname;
	}
	public void setLyricname(String lyricname) {
		this.lyricname = lyricname;
	}
	
	public String getMoviename() {
		return moviename;
	}
	public void setMoviename(String moviename) {
		this.moviename = moviename;
	}
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public String getWriterName() {
		return writerName;
	}
	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}
	public Timestamp getMovieReleaseDate() {
		return movieReleaseDate;
	}
	public void setMovieReleaseDate(Timestamp movieReleaseDate) {
		this.movieReleaseDate = movieReleaseDate;
	}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
