package com.lyrics.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class L_allTimeHits implements Serializable {

	private int lyricId;
	private String lyricTitle;
	private String writerName;
	private String url;
	private String movieName;
	private String movie_id;
	private Timestamp releaseDate;
	
	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public Timestamp getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Timestamp releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getMovie_id() {
		return movie_id;
	}

	public void setMovie_id(String movie_id) {
		this.movie_id = movie_id;
	}

	public int getLyricId() {
		return lyricId;
	}

	public void setLyricId(int lyricId) {
		this.lyricId = lyricId;
	}

	public String getLyricTitle() {
		return lyricTitle;
	}

	public void setLyricTitle(String lyricTitle) {
		this.lyricTitle = lyricTitle;
	}

	public String getWriterName() {
		return writerName;
	}

	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setMovieId(int movieId) {
		// TODO Auto-generated method stub
		
	}

}



