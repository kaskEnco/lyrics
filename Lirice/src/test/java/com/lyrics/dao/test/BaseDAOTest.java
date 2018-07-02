package com.lyrics.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;

import com.lyrics.Constants;
import com.lyrics.dao.BaseDAO;
import com.lyrics.dao.LyricConnectionFactory;
import com.lyrics.dao.LyricLanguageDAO;
import com.lyrics.dao.LyricMovieDAO;
import com.lyrics.dao.LyricYearDAO;
import com.lyrics.dao.MemcachedConncetionFactory;
import com.lyrics.model.L_language;
import com.lyrics.model.L_lyrics;
import com.lyrics.model.L_movie;
import com.lyrics.model.L_teluguLyrics;
import com.lyrics.model.L_year;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.OperationTimeoutException;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

class BaseDAOTest {

	@Mock
	BaseDAO base;
	
	BaseDAO baseMock = mock(BaseDAO.class);
	
	@Test
	public  void getConnection() throws SQLException {
		
		Connection connMock = DriverManager.getConnection("jdbc:mysql://localhost:3306/lyrics","root","root");
		when(baseMock.getConnection()).thenReturn(connMock);
		Connection connection = baseMock.getConnection();
		assertEquals(connMock, connection);
		assertNotNull(connMock);
		
	}
	
	@Test
	public void getMemcached() throws OperationTimeoutException, SQLException {
		
		MemcachedClient cacheMock = MemcachedConncetionFactory.getInstance().getConnection();
		when(baseMock.getMemcacheConnection()).thenReturn(cacheMock);
		MemcachedClient cacheClientActual = baseMock.getMemcacheConnection();
		assertEquals(cacheMock, cacheClientActual);
		
	}
	
	@Test
	public void getAllYears() {
		
		List<L_year> yearsMock = new ArrayList<L_year>();
		L_year year = new L_year();
		year.setLyircYear(2000);
		year.setYearId(1);
		yearsMock.add(year);
		when(baseMock.findAllYears()).thenReturn(yearsMock);
		List<L_year> years = baseMock.findAllYears();
		assertEquals(1, years.get(0).getYearId());
		assertEquals(2000, years.get(0).getLyircYear());
				
	}
	
	@Test
	public void getAllLanguges() {
		
		List<L_language> languagesMock = new ArrayList<L_language>();
		L_language lang = new L_language();
		lang.setLangId(1);
		lang.setLanguage("hindi");
		languagesMock.add(lang);
		when(baseMock.findAllLanguges()).thenReturn(languagesMock);
		List<L_language> languages = baseMock.findAllLanguges();
		assertEquals(1, languages.get(0).getLangId());
		assertEquals("hindi", languages.get(0).getLanguage());
	
	}
	
	
	@Test
	public void getAllLyrics(){
		
		List<L_lyrics> lyricsMock = new ArrayList<L_lyrics>();
		L_lyrics lyrics = new L_lyrics();
		lyrics.set_id(1);
		lyrics.setLyricContent("hello hello");
		lyricsMock.add(lyrics);
		when(baseMock.findAllLyrics()).thenReturn(lyricsMock);
		List<L_lyrics> lyricsFiles = baseMock.findAllLyrics();
		assertEquals(1, lyricsFiles.get(0).get_id());
		assertEquals("hello hello", lyricsFiles.get(0).getLyricContent());
		
	}
	
	@Test
	public void getAllMovies() {
		
		List<L_movie> moviesMock =new ArrayList<L_movie>();
		L_movie movie = new L_movie();
		movie.setMovieId(1);
		movie.setMovieName("hello");
		moviesMock.add(movie);
		when(baseMock.findAllMovies()).thenReturn(moviesMock);
		List<L_movie> movies = baseMock.findAllMovies();
		assertEquals(1, movies.get(0).getMovieId());
		assertEquals("hello", movies.get(0).getMovieName());
		
	}
	
	@Test
	public void getAllTeluguLyrics() {
		
		List<L_lyrics> telLyricsMock = new ArrayList<L_lyrics>();
		L_lyrics lyric = new L_lyrics();
		lyric.set_id(1);
		lyric.setLyricTitle("Dhooram");
		telLyricsMock.add(lyric);
		when(baseMock.findAllTeluguLyrics()).thenReturn(telLyricsMock);
		List<L_lyrics> lyrics = baseMock.findAllTeluguLyrics();
		assertEquals(1, lyrics.get(0).get_id());
		assertEquals("Dhooram", lyrics.get(0).getLyricTitle());
			
	}
	
	@Test
	public void Views() {
		//doAnswer(Answers.CALLS_REAL_METHODS.get()).when(baseMock);
       
	}
	/*
	 * BaseDAO unitbase; BaseDAOTest baseTest; Connection connection = null;
	 * 
	 * SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); Timestamp tm
	 * = null;
	 * 
	 * Connection getConnection() { Connection connection = null; unitbase = new
	 * BaseDAO(); try { connection =
	 * LyricConnectionFactory.getInstance().getConnection();
	 * assertNotNull(connection); } catch (SQLException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } finally { unitbase.closeConnection(); }
	 * return connection; }
	 * 
	 * public void closePtmt(PreparedStatement ptmt) {
	 * 
	 * try { if (ptmt != null) ptmt.close();
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * }
	 * 
	 * public void closeConnection() {
	 * 
	 * try {
	 * 
	 * if (connection != null) connection.close();
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); } catch (Exception e) {
	 * e.printStackTrace(); } finally {
	 * 
	 * connection = null; } }
	 * 
	 * public static void closeResultset(ResultSet resultSet) {
	 * 
	 * try { if (resultSet != null) resultSet.close(); } catch (SQLException e) {
	 * e.printStackTrace(); } }
	 * 
	 * @Test void years() { unitbase = new BaseDAO();
	 * 
	 * List<L_year> years = new ArrayList<L_year>(); L_year year = null;
	 * PreparedStatement ptmt = null; ResultSet resultSet = null; String queryString
	 * = "SELECT * FROM l_year "; try { connection = getConnection(); ptmt =
	 * connection.prepareStatement(queryString); resultSet = ptmt.executeQuery();
	 * while (resultSet.next()) { year = new L_year(); year.setYearId(1);
	 * year.setLyircYear(1960); years.add(year);
	 * 
	 * }
	 * 
	 * } catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } finally { closeResultset(resultSet); closePtmt(ptmt);
	 * closeConnection(); }
	 * 
	 * assertNotNull(years); assertNotNull(year.getLyircYear());
	 * assertNotNull(year.getYearId()); }
	 * 
	 * @Test void connection() { Connection connection = null; unitbase = new
	 * BaseDAO(); try { connection =
	 * LyricConnectionFactory.getInstance().getConnection();
	 * assertNotNull(connection); } catch (SQLException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } finally { unitbase.closeConnection(); } }
	 * 
	 * 
	 * @Test void findAllYears() { unitbase = new BaseDAO(); List<L_year> years =
	 * unitbase.findAllYears(); assertNotNull(years);
	 * 
	 * List<L_year> years = new ArrayList<L_year>(); L_year year = null;
	 * PreparedStatement ptmt = null; ResultSet resultSet = null; String queryString
	 * = "SELECT * FROM l_year "; try { connection = getConnection(); ptmt =
	 * connection.prepareStatement(queryString); resultSet = ptmt.executeQuery();
	 * while (resultSet.next()) { year = new L_year();
	 * year.setYearId(resultSet.getInt("id"));
	 * year.setLyircYear(resultSet.getInt("lyric_year")); years.add(year);
	 * 
	 * }
	 * 
	 * } catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } finally { closeResultset(resultSet); closePtmt(ptmt);
	 * closeConnection(); }
	 * 
	 * 
	 * }
	 * 
	 * 
	 * @Test void allLanguages() { unitbase = new BaseDAO(); List<L_language>
	 * languages = unitbase.findAllLanguges(); assertNotNull(languages); }
	 * 
	 * @Test void allLyrics() { unitbase = new BaseDAO(); List<L_lyrics> lyrics =
	 * unitbase.findAllLyrics(); assertNotNull(lyrics); }
	 * 
	 * @Test void allMovies() { unitbase = new BaseDAO(); List<L_movie> movies =
	 * unitbase.findAllMovies(); assertNotNull(movies); }
	 * 
	 * @Test void allTeluguLyrics() { unitbase = new BaseDAO(); List<L_lyrics>
	 * teluguLyrics = unitbase.findAllTeluguLyrics(); assertNotNull(teluguLyrics); }
	 * 
	 * @Test public void findAllLanguges() { List<L_language> languages = null;
	 * L_language lang = null; PreparedStatement ptmt = null; ResultSet resultSet =
	 * null; String queryString = "SELECT * FROM l_language "; try {
	 * 
	 * connection = getConnection(); ptmt =
	 * connection.prepareStatement(queryString); resultSet = ptmt.executeQuery();
	 * languages = new ArrayList<L_language>(); while (resultSet.next()) { lang =
	 * new L_language(); lang.setLangId(1); lang.setLanguage("Telugu");
	 * languages.add(lang); }
	 * 
	 * } catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * assertNotNull(lang.getLangId()); assertNotNull(lang.getLanguage()); }
	 * 
	 * @Test public void findAllLyrics() { List<L_lyrics> lyrics = new
	 * ArrayList<L_lyrics>(); LyricMovieDAO movieDAO = new LyricMovieDAO(); L_lyrics
	 * lyric = null; String queryString = "SELECT * FROM l_lyrics ";
	 * PreparedStatement ptmt = null; ResultSet resultSet = null; try { connection =
	 * getConnection(); ptmt = connection.prepareStatement(queryString); resultSet =
	 * ptmt.executeQuery(); while (resultSet.next()) { lyric = new L_lyrics();
	 * lyric.setLyricId(1); lyric.setLyricTitle("Dhooram");
	 * lyric.setLyricContent("Dhooram dhooram .....");
	 * lyric.setMovie(movieDAO.findById(1)); lyric.setWriterName("Anantha_Sriram");
	 * lyric.setLyricViews(123);
	 * 
	 * java.util.Date date = formatter.parse("20-02-2017"); tm = new
	 * Timestamp(date.getTime());
	 * 
	 * lyric.setCreationDate(tm); lyric.setUpdationDate(tm);
	 * lyric.setUrl("Xen6_YnPKsY"); lyrics.add(lyric); }
	 * 
	 * } catch (SQLException | ParseException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } finally { closeResultset(resultSet);
	 * closePtmt(ptmt); closeConnection(); }
	 * 
	 * }
	 * 
	 * @Test public void findAllMovies() { List<L_movie> movies = new
	 * ArrayList<L_movie>(); LyricLanguageDAO langDAO = new LyricLanguageDAO();
	 * LyricYearDAO yearDAO = new LyricYearDAO(); PreparedStatement ptmt = null;
	 * ResultSet resultSet = null; L_movie movie = null; String queryString =
	 * "SELECT * FROM l_movie "; try { connection = getConnection(); ptmt =
	 * connection.prepareStatement(queryString); resultSet = ptmt.executeQuery();
	 * while (resultSet.next()) { movie = new L_movie(); movie.setMovieId(1);
	 * movie.setLanguage(langDAO.findById(1)); movie.setYear(yearDAO.findById(1));
	 * movie.setMovieName("Arjun Reddy");
	 * 
	 * Date date = formatter.parse("20-02-2017"); tm = new
	 * Timestamp(date.getTime());
	 * 
	 * movie.setMovieReleaseDate(tm); movie.setCreationDate(tm);
	 * movie.setUpdationDate(tm); movies.add(movie); }
	 * 
	 * } catch (SQLException | ParseException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } finally { closeResultset(resultSet);
	 * closePtmt(ptmt); closeConnection(); } assertNotNull(movie.getCreationDate());
	 * assertNotNull(movie.getLanguage()); assertNotNull(movie.getMovieId());
	 * assertNotNull(movie.getMovieName());
	 * assertNotNull(movie.getMovieReleaseDate());
	 * assertNotNull(movie.getUpdationDate()); assertNotNull(movie.getYear());
	 * 
	 * }
	 * 
	 * @Test public void findAllTeluguLyrics() {
	 * 
	 * L_lyrics content = null; List<L_lyrics> teluguLyrics = new
	 * ArrayList<L_lyrics>(); MongoClient mongoClient = new MongoClient("localhost",
	 * 27017); DB db = mongoClient.getDB("lyrics"); DBCollection collection =
	 * db.getCollection("teluguLyrics"); DBCursor cursor = collection.find();
	 * 
	 * int n = collection.find().count(); Object value = null; for (int i = 0; i <
	 * n; i++) { value = cursor.next(); JSONObject obj = new JSONObject((Map)
	 * value); content = new L_lyrics(); content.set_id(1);
	 * content.setLyricContent("Dhooram Dhooram ...");
	 * content.setUrl("Xen6_YnPKsY");
	 * 
	 * teluguLyrics.add(content); } assertNotNull(content.get_id());
	 * assertNotNull(content.getLyricContent()); assertNotNull(content.getUrl()); }
	 * 
	 * @Test public void Count() { PreparedStatement ptmt = null; int resultSet = 0
	 * ; String queryString =
	 * "update l_lyrics set lyric_views = lyric_views+1 where id = ? "; try {
	 * connection = getConnection(); ptmt =
	 * connection.prepareStatement(queryString); ptmt.setInt(1, 1); resultSet =
	 * ptmt.executeUpdate(); connection.close(); while(resultSet.next()) { int count
	 * = resultSet.getInt(1); } }catch (SQLException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } assertNotNull(resultSet); }
	 * 
	 */
}
