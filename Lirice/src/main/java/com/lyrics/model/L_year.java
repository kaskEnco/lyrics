package com.lyrics.model;

public class L_year {
	// @Id
	// private Long id;
	//
	// @Column(name = "lyric_year")
	// private Long lyircYear;

	private int id;
	private int lyircYear;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLyircYear() {
		return lyircYear;
	}

	public void setLyircYear(int lyircYear) {
		this.lyircYear = lyircYear;
	}

}
