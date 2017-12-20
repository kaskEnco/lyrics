package com.lyrics.model;

public class TrendingMovies {

	private L_movie movie;
	private int lyricViews;
	private String lyric_name;

	public L_movie getMovie() {
		return movie;
	}

	public void setMovie(L_movie movie) {
		this.movie = movie;
	}

	public int getLyricViews() {
		return lyricViews;
	}

	public void setLyricViews(int lyricViews) {
		this.lyricViews = lyricViews;
	}

	public String getLyric_name() {
		return lyric_name;
	}

	public void setLyric_name(String lyric_name) {
		this.lyric_name = lyric_name;
	}
}
