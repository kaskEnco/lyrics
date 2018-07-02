package com.lyrics.dao.test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.lyrics.dao.LyricLanguageDAO;

import com.lyrics.model.L_language;


@RunWith(MockitoJUnitRunner.class)
public class LyricLanguageDAOTest {


	
	@Mock
	LyricLanguageDAO lyricLanguage;

	// with Mocking
	
	@Test
	public void findLanguageById() {
	
		L_language language = new L_language();
		language.setLangId(1);
		language.setLanguage("Telugu");
		
		//when(lyricLanguage.findById(anyInt())).thenReturn(language);
		when(lyricLanguage.findById(anyInt())).thenReturn(language);

		L_language lang = lyricLanguage.findById(1);
		assertEquals(1, lang.getLangId());
		assertEquals("Telugu", lang.getLanguage());
		
	}

	//without Mocking
/*	
	@Test
	public void test() {
		lyricLanguage = new LyricLanguageDAO();
		L_language language = lyricLanguage.findById(1);
		String actual = language.getLanguage();
		String expected = "ENGLISH";
		assertNotNull(actual);
		assertEquals(expected, actual);
	}*/

}
