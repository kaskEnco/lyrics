package com.lyrics.dao.test;

import static org.junit.Assert.*;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.lyrics.dao.LyricLanguageDAO;
import com.lyrics.dao.LyricYearDAO;
import com.lyrics.model.L_language;
import com.lyrics.model.L_year;


@RunWith(MockitoJUnitRunner.class)
public class LyricYearDAOTest {

	@Mock
	LyricYearDAO yearValue;

	@Test
	public void getYearById() {

		L_year year = new L_year();
		year.setLyircYear(1960);
		year.setYearId(1);

		// LyricYearDAO yearValue = mock(LyricYearDAO.class); //we can use if we dont
		// use @Mock Annotation
		when(yearValue.findById(anyInt())).thenReturn(year);// if we want to get same result even we pass different
															// values as id then we use anyInt()
		// for strings we have anySrting() method //this is called as argument matcher

		L_year yearById = yearValue.findById(0);
		assertEquals(1, yearById.getYearId());
		assertEquals(1960, yearById.getLyircYear());
	}

	@Test
	public void getIdByYear() {

		L_year year = new L_year();
		year.setLyircYear(1960);
		year.setYearId(1);

		// LyricYearDAO yearValue = mock(LyricYearDAO.class);
		//we can use if we dont use @Mock Annotation
		when(yearValue.findByYear(anyInt())).thenReturn(year);// if we want to get same result even we pass different
																// values as id then we use anyInt()
		// for strings we have anySrting() method //this is called as argument matcher

		L_year idByYear = yearValue.findByYear(1);
		assertEquals(1, idByYear.getYearId());
		assertEquals(1960, idByYear.getLyircYear());
	}

	// with out mocking
	/*
	 * @Test public void yearById() { LyricYearDAO test = new LyricYearDAO(); int
	 * yearExcepted = 1961; L_year yearObj = test.findById(2);
	 * System.out.println(yearObj); System.out.println(yearObj.toString()); int
	 * yearActual = yearObj.getLyircYear(); //assertNotNull(yearObj);
	 * assertEquals(yearExcepted, yearActual); }
	 * 
	 * @Test public void idByYear() { LyricYearDAO test = new LyricYearDAO(); int
	 * idExcepted = 42; L_year yearObj = test.findByYear(2000); int idActual =
	 * yearObj.getYearId(); //assertNotNull(yearObj); assertEquals(idExcepted,
	 * idActual); }
	 */
}
