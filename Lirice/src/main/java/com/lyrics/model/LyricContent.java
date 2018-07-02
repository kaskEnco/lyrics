package com.lyrics.model;

import java.io.Serializable;

public class LyricContent implements Serializable {

	private String lyricContent;
	private String url;

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

}
