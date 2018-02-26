package com.lyrics.model;

import java.io.Serializable;

public class LyricContent implements Serializable {

	private String lyricContent;

	public String getLyricContent() {
		return lyricContent;
	}

	public void setLyricContent(String lyricContent) {
		this.lyricContent = lyricContent;
	}
}
