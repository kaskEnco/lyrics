package com.lyrics.model;

import java.sql.Timestamp;

public class L_teluguLyrics {


	private String lyricContent;
	private String url;
	private int _id;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLyricContent() {
		return lyricContent;
	}

	public void setLyricContent(String lyricContent) {
		this.lyricContent = lyricContent;
	}


	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	
}
