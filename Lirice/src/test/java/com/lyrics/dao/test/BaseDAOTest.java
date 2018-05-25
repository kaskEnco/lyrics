package com.lyrics.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import com.lyrics.dao.BaseDAO;
import com.lyrics.dao.LyricConnectionFactory;
import com.lyrics.model.L_language;
import com.lyrics.model.L_lyrics;
import com.lyrics.model.L_movie;
import com.lyrics.model.L_year;

class BaseDAOTest {
	
	BaseDAO unitbase;
	
	@Test
	void test() {
		unitbase = new BaseDAO();
		Object expected = "[{\"yearId\":1,\"lyircYear\":1960},{\"yearId\":2,\"lyircYear\":1961},{\"yearId\":3,\"lyircYear\":1962},{\"yearId\":4,\"lyircYear\":1963},{\"yearId\":5,\"lyircYear\":1964},{\"yearId\":6,\"lyircYear\":1965},{\"yearId\":7,\"lyircYear\":1966},{\"yearId\":8,\"lyircYear\":1967},{\"yearId\":9,\"lyircYear\":1968},{\"yearId\":10,\"lyircYear\":1969},{\"yearId\":11,\"lyircYear\":1970},{\"yearId\":12,\"lyircYear\":1971},{\"yearId\":13,\"lyircYear\":1972},{\"yearId\":14,\"lyircYear\":1973},{\"yearId\":15,\"lyircYear\":1974},{\"yearId\":16,\"lyircYear\":1975},{\"yearId\":17,\"lyircYear\":1976},{\"yearId\":18,\"lyircYear\":1977},{\"yearId\":19,\"lyircYear\":1978},{\"yearId\":21,\"lyircYear\":1979},{\"yearId\":22,\"lyircYear\":1980},{\"yearId\":23,\"lyircYear\":1981},{\"yearId\":24,\"lyircYear\":1982},{\"yearId\":25,\"lyircYear\":1983},{\"yearId\":26,\"lyircYear\":1984},{\"yearId\":27,\"lyircYear\":1985},{\"yearId\":28,\"lyircYear\":1986},{\"yearId\":29,\"lyircYear\":1987},{\"yearId\":30,\"lyircYear\":1988},{\"yearId\":31,\"lyircYear\":1989},{\"yearId\":32,\"lyircYear\":1990},{\"yearId\":33,\"lyircYear\":1991},{\"yearId\":34,\"lyircYear\":1992},{\"yearId\":35,\"lyircYear\":1993},{\"yearId\":36,\"lyircYear\":1994},{\"yearId\":37,\"lyircYear\":1995},{\"yearId\":38,\"lyircYear\":1996},{\"yearId\":39,\"lyircYear\":1997},{\"yearId\":40,\"lyircYear\":1998},{\"yearId\":41,\"lyircYear\":1999},{\"yearId\":42,\"lyircYear\":2000},{\"yearId\":43,\"lyircYear\":2001},{\"yearId\":44,\"lyircYear\":2002},{\"yearId\":45,\"lyircYear\":2003},{\"yearId\":46,\"lyircYear\":2004},{\"yearId\":47,\"lyircYear\":2005},{\"yearId\":48,\"lyircYear\":2006},{\"yearId\":49,\"lyircYear\":2007},{\"yearId\":50,\"lyircYear\":2008},{\"yearId\":51,\"lyircYear\":2009},{\"yearId\":52,\"lyircYear\":2010},{\"yearId\":53,\"lyircYear\":2011},{\"yearId\":54,\"lyircYear\":2012},{\"yearId\":55,\"lyircYear\":2013},{\"yearId\":56,\"lyircYear\":2014},{\"yearId\":57,\"lyircYear\":2015},{\"yearId\":58,\"lyircYear\":2016},{\"yearId\":59,\"lyircYear\":2017},{\"yearId\":60,\"lyircYear\":2018},{\"yearId\":61,\"lyircYear\":2019},{\"yearId\":62,\"lyircYear\":2020},{\"yearId\":63,\"lyircYear\":2021},{\"yearId\":64,\"lyircYear\":2022},{\"yearId\":65,\"lyircYear\":2023},{\"yearId\":66,\"lyircYear\":2024},{\"yearId\":67,\"lyircYear\":2025},{\"yearId\":68,\"lyircYear\":2026},{\"yearId\":69,\"lyircYear\":2027},{\"yearId\":70,\"lyircYear\":2028},{\"yearId\":71,\"lyircYear\":2029},{\"yearId\":72,\"lyircYear\":2030}]";
     		/*JSONObject obj = new JSONObject();
     		obj.toJSONString();*/
		List<L_year> years = unitbase.findAllYears();
	//System.out.println(years.equals(expected));
	
/*		//List<L_year> actual = null;
		for (L_year list : years) {
			if (list.getYearId() == 5) {
				L_year yearObj = list;
			}
		}
*/	
		assertNotNull(years);
		//assertEquals(expected.toString(), years.toString());
	}
	
	@Test
	void connection()
	{
		Connection connection = null;
		unitbase = new BaseDAO();
		try {
			connection = LyricConnectionFactory.getInstance().getConnection();
			assertNotNull(connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			unitbase.closeConnection();
		}
	}
	
	@Test
	void allYears()
	{
		unitbase = new BaseDAO();
		List<L_year> years = unitbase.findAllYears();
		assertNotNull(years);
	}
	
	@Test
	void allLanguages()
	{
		unitbase = new BaseDAO();
		List<L_language> languages = unitbase.findAllLanguges();
		assertNotNull(languages);
	}
	
	@Test
	void allLyrics()
	{
		unitbase = new BaseDAO();
		List<L_lyrics> lyrics = unitbase.findAllLyrics();
		assertNotNull(lyrics);
	}
	
	@Test
	void allMovies()
	{
		unitbase = new BaseDAO();
		List<L_movie> movies = unitbase.findAllMovies();
		assertNotNull(movies);
	}
	
	@Test
	void allTeluguLyrics()
	{
		unitbase = new BaseDAO();
		List<L_lyrics> teluguLyrics = unitbase.findAllTeluguLyrics();
		assertNotNull(teluguLyrics);
	}
	
	
}
