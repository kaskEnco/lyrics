package com.lyrics.dao.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.lyrics.dao.LyricLanguageDAO;
import com.lyrics.model.L_language;

class LyricLanguageDAOTest {

	LyricLanguageDAO lyrictest;
	
	@Test
	void test() {
		lyrictest = new LyricLanguageDAO();
		L_language language = lyrictest.findById(1);
		String actual  = language.getLanguage();
		String expected = "ENGLISH";
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

}
