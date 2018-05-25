package com.lyrics.dao.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.lyrics.dao.LyricYearDAO;
import com.lyrics.model.L_year;

public class LyricYearDAOTest {

	@Test
	public void yearById() {
		LyricYearDAO test = new LyricYearDAO();
		int yearExcepted = 1961;
		L_year yearObj = test.findById(2);
		System.out.println(yearObj);
		System.out.println(yearObj.toString());
		int yearActual = yearObj.getLyircYear();
		//assertNotNull(yearObj);
		assertEquals(yearExcepted, yearActual);
	}
	@Test
	public void idByYear() {
		LyricYearDAO test = new LyricYearDAO();
		int idExcepted = 42;
		L_year yearObj = test.findByYear(2000);
		int idActual = yearObj.getYearId();
		//assertNotNull(yearObj);
		assertEquals(idExcepted, idActual);
	}
}
