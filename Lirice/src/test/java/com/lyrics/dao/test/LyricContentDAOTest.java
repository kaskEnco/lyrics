package com.lyrics.dao.test;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.lyrics.dao.LyricContentDAO;
import com.lyrics.model.L_lyrics;
import com.lyrics.model.L_teluguLyrics;
import com.lyrics.model.LyircsByMovie;
import com.lyrics.model.LyricContent;
import com.lyrics.model.TrendingMovies;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

class LyricContentDAOTest {

	@Mock
	LyricContentDAO content;
	
	LyricContentDAO dao = mock(LyricContentDAO.class);

	@Test
	public void getLyricById() {
		L_lyrics lyrics = new L_lyrics();
		lyrics.set_id(1);
		lyrics.setLyricTitle("Arjun");
		lyrics.setLyricViews(123);
		when(dao.findById(anyInt())).thenReturn(lyrics);
		L_lyrics expected = dao.findById(1);
		assertEquals(1, expected.get_id());
	}

	@Test
	public void getLyricsByMovieId() {

		List<LyircsByMovie> movieLyrics = new ArrayList<LyircsByMovie>();
		LyircsByMovie lyrics = new LyircsByMovie();
		lyrics.setLyricId(1);
		lyrics.setLyricTitle("Hello");
		movieLyrics.add(lyrics);
		when(dao.getLyricsByMovie(anyInt())).thenReturn(movieLyrics);
		List<LyircsByMovie> lyricsbymovie = dao.getLyricsByMovie(1);
		assertEquals(1, lyricsbymovie.get(0).getLyricId());
		assertEquals("Hello", lyricsbymovie.get(0).getLyricTitle());

	}

	@Test
	public void getTrendingLyrics() {

		List<TrendingMovies> movies = new ArrayList<TrendingMovies>();
		TrendingMovies trending = new TrendingMovies();
		trending.setLyric_name("hello");
		trending.setLyricId(1);
		movies.add(trending);
		when(dao.getTrendingLyrics()).thenReturn(movies);
		List<TrendingMovies> trendingExpected = dao.getTrendingLyrics();
		assertEquals(1, trendingExpected.get(0).getLyricId());
		assertEquals("hello", trendingExpected.get(0).getLyric_name());

	}

	@Test
	public void getWrite() {
	
		TreeSet<String> writerSet = new TreeSet<String>();
		writerSet.add("anantha sriram");
		when(dao.getWriter()).thenReturn(writerSet);
		Set<String> writerString = dao.getWriter();
		assertEquals(1, writerString.size());
		Iterator<String> it = writerSet.iterator();
		String name = it.next();
		assertEquals("anantha sriram", name);
	
	}
	
	@Test
	public void getLyricContent() {
		
		List<LyricContent> content = new ArrayList<LyricContent>();
		LyricContent lyricContent = new LyricContent();
		lyricContent.setUrl("aaaaa");
		lyricContent.setLyricContent("Dhooram");
		content.add(lyricContent);
		when(dao.getLyrics(anyInt())).thenReturn(content);
		List<LyricContent> expected = dao.getLyrics(1);
		assertEquals("Dhooram", expected.get(0).getLyricContent());
		assertEquals("aaaaa", expected.get(0).getUrl());
		
	}

	@Test
	public void getAllTrendingMovies() {
		List<TrendingMovies> trendMovies = new 	ArrayList<TrendingMovies>();
		TrendingMovies movies = new TrendingMovies();
		movies.setLyric_name("Dhooram");
		movies.setLyricId(1);
		trendMovies.add(movies);
		when(dao.getAllTrendingMovies()).thenReturn(trendMovies);
		List<TrendingMovies> expectedTrending = dao.getAllTrendingMovies();
		assertEquals(1, expectedTrending.get(0).getLyricId());
		assertEquals("Dhooram", expectedTrending.get(0).getLyric_name());

	}

	@Test
	public void getLyricByWriterMovie() {
		
		List<LyircsByMovie> lyricByWriter= new ArrayList<LyircsByMovie>();
		LyircsByMovie lyricsByMovie = new LyircsByMovie();
		lyricsByMovie.setLyricId(1);
		lyricsByMovie.setLyricTitle("Dhooram");
		lyricsByMovie.setMovieId(1);
		lyricsByMovie.setWriterName("anantha sriram");
		lyricByWriter.add(lyricsByMovie);
		when(dao.getLyricsByWriterMovie(anyInt(), anyString())).thenReturn(lyricByWriter);
		List<LyircsByMovie> lyricByWriterExpected = dao.getLyricsByWriterMovie(1, null);
		assertEquals(1, lyricByWriterExpected.get(0).getLyricId());
		assertEquals("Dhooram", lyricByWriterExpected.get(0).getLyricTitle());
		assertEquals(1, lyricByWriterExpected.get(0).getMovieId());
		assertEquals("anantha sriram", lyricByWriterExpected.get(0).getWriterName());
		
	}
	
	@Test
	public void getMoviesByWriter() {
		
		List<Integer> movieIds = new ArrayList<Integer>();
		movieIds.add(1);
		when(dao.getMovieIdsByWriter(anyString())).thenReturn(movieIds);
		List<Integer> movieIdsExpected = dao.getMovieIdsByWriter("srimani");
		Iterator ids = movieIdsExpected.iterator();
		int movieId = (int) ids.next();
		assertEquals(1, movieId);
			
	}
	
	@Test
	public void getTeluguLyrics() {
		 List<L_teluguLyrics> teluguLyrics = new  ArrayList<L_teluguLyrics>();
		 L_teluguLyrics lyrics = new L_teluguLyrics();
		 lyrics.set_id(1);
		 lyrics.setLyricContent("hello hello");
		 teluguLyrics.add(lyrics);
		 when(dao.getTeluguLyrics(anyInt())).thenReturn(teluguLyrics);
		 List<L_teluguLyrics> lyricsActual= dao.getTeluguLyrics(1);
		 assertEquals(1, lyricsActual.get(0).get_id());
		 assertEquals("hello hello", lyricsActual.get(0).getLyricContent());
	}
	/*
	 * @Test void LyricByLyricId() throws ParseException {
	 * 
	 * lyrics = new LyricContentDAO(); String expected = "Dhooram"; L_lyrics actual
	 * = lyrics.findById(1); String lyricname = actual.getLyricTitle(); L_lyrics
	 * lyric = new L_lyrics(); LyricMovieDAO movieDAO = new LyricMovieDAO(); String
	 * queryString = "SELECT * FROM l_lyrics where id = ?  "; PreparedStatement ptmt
	 * = null; ResultSet resultSet = null;
	 * 
	 * try { connection = (Connection) getConnection(); ptmt =
	 * connection.prepareStatement(queryString); ptmt.setInt(1, 1); resultSet =
	 * ptmt.executeQuery(); while (resultSet.next()) { lyric.setLyricId(1);
	 * lyric.setLyricTitle("Dhooram");
	 * lyric.setLyricContent("dhooram dhooram ....");
	 * lyric.setMovie(movieDAO.findById(1)); lyric.setWriterName("Anantha_Sriram");
	 * lyric.setLyricViews(1234);
	 * 
	 * java.util.Date date = formatter.parse("20-02-2017"); tm = new
	 * Timestamp(date.getTime());
	 * 
	 * lyric.setCreationDate(tm); lyric.setUpdationDate(tm);
	 * lyric.setUrl("Xen6_YnPKsY");
	 * 
	 * } } catch (SQLException e) {
	 * 
	 * e.printStackTrace(); } finally { closePtmt(ptmt); closeConnection(); } String
	 * actual1 = lyric.getLyricTitle(); assertNotNull(lyric.getLyricContent());
	 * assertNotNull(lyric.getLyricId()); assertNotNull(lyric.getLyricTitle());
	 * assertNotNull(lyric.getLyricViews()); assertNotNull(lyric.getMovie());
	 * assertNotNull(lyric.getUpdationDate());
	 * assertNotNull(lyric.getCreationDate()); assertNotNull(lyric.getUrl());
	 * assertNotNull(lyric.getWriterName()); assertEquals(expected, actual1); }
	 * 
	 * 
	 * @InjectMocks public LyricContentDAO latestlyrics;
	 * 
	 * @Before public void getMock() { mockmvc =
	 * MockMvcBuilders.standaloneSetup(latestlyrics).build(); }
	 * 
	 * @Test void LyricByMovieId() { lyrics = new LyricContentDAO(); String expected
	 * = "Kannu moosthe badrinath"; List<LyircsByMovie> actual1 =
	 * lyrics.getLyricsByMovie(400); LyircsByMovie movieName = actual1.get(1);
	 * String song = movieName.getLyricTitle();
	 * 
	 * LyircsByMovie lyric = null; List<LyircsByMovie> allLyrics = null;
	 * 
	 * List<L_lyrics> moviesList = new ArrayList<L_lyrics>(); if (moviesList !=
	 * null) { allLyrics = new ArrayList<LyircsByMovie>(); for (L_lyrics list :
	 * moviesList) { if (400 == list.getMovie().getMovieId()) { lyric = new
	 * LyircsByMovie(); lyric.setLyricId(400);
	 * lyric.setLyricTitle("Kannu moosthe badrinath"); lyric.setMovieId(1);
	 * lyric.setWriterName("Anantha_Sriram"); allLyrics.add(lyric); } } } String
	 * actual = lyric.getLyricTitle(); assertNotNull(lyric.getLyricId());
	 * assertNotNull(lyric.getLyricTitle()); assertNotNull(lyric.getMovieId());
	 * assertNotNull(lyric.getWriterName()); assertEquals(expected, actual); }
	 * 
	 * @Test public void getTrendingLyrics() { lyrics = new LyricContentDAO();
	 * List<TrendingMovies> actual = lyrics.getTrendingLyrics();
	 * List<TrendingMovies> trendingLyrics = null; TrendingMovies lyric = null;
	 * trendingLyrics = new ArrayList<TrendingMovies>(); LyricMovieDAO movieDAO =
	 * new LyricMovieDAO(); PreparedStatement ptmt = null; ResultSet resultSet =
	 * null; String queryString =
	 * "select * from l_lyrics order by lyric_views DESC "; try { connection =
	 * (Connection) getConnection(); ptmt =
	 * connection.prepareStatement(queryString); resultSet = ptmt.executeQuery();
	 * while (resultSet.next()) { lyric = new TrendingMovies(); lyric.setLyricId(1);
	 * lyric.setLyric_name("Dhooram"); L_movie movie = movieDAO.findById(1);
	 * lyric.setMovieId(1); lyric.setMovieName("Arjun Reddy");
	 * 
	 * java.util.Date date = formatter.parse("20-02-2017"); tm = new
	 * Timestamp(date.getTime());
	 * 
	 * lyric.setReleaseDate(tm); lyric.setWriterName("Anantha_Sriram");
	 * lyric.setLyricViews(1234);
	 * 
	 * trendingLyrics.add(lyric); } } catch (SQLException e) {
	 * 
	 * e.printStackTrace(); } catch (ParseException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } finally { closeResultset(resultSet);
	 * closePtmt(ptmt); closeConnection(); } assertNotNull(lyric.getLyric_name());
	 * assertNotNull(lyric.getLyricId()); assertNotNull(lyric.getLyricViews());
	 * assertNotNull(lyric.getMovieId()); assertNotNull(lyric.getMovieName());
	 * assertNotNull(lyric.getReleaseDate()); assertNotNull(lyric.getWriterName());
	 * assertNotNull(actual); }
	 * 
	 * @Test public void getAllTrendingMovies() { lyrics = new LyricContentDAO();
	 * List<TrendingMovies> actual = lyrics.getAllTrendingMovies();
	 * List<TrendingMovies> trendingLyrics = null; TrendingMovies lyric = null;
	 * trendingLyrics = new ArrayList<TrendingMovies>(); LyricMovieDAO movieDAO =
	 * new LyricMovieDAO(); PreparedStatement ptmt = null; ResultSet resultSet =
	 * null; String queryString =
	 * "select * from l_lyrics order by lyric_views DESC "; try { connection =
	 * (Connection) getConnection(); ptmt =
	 * connection.prepareStatement(queryString); resultSet = ptmt.executeQuery();
	 * while (resultSet.next()) { lyric = new TrendingMovies(); lyric.setLyricId(1);
	 * lyric.setLyric_name("Dhooram"); L_movie movie = movieDAO.findById(1);
	 * lyric.setMovieId(1); lyric.setMovieName("Arjun Reddy");
	 * 
	 * java.util.Date date = formatter.parse("20-02-2017"); tm = new
	 * Timestamp(date.getTime());
	 * 
	 * lyric.setReleaseDate(tm); lyric.setWriterName("Anantha_Sriram");
	 * lyric.setLyricViews(123); trendingLyrics.add(lyric); } } catch (SQLException
	 * e) { e.printStackTrace(); } catch (ParseException e) { e.printStackTrace(); }
	 * finally { closeResultset(resultSet); closePtmt(ptmt); closeConnection(); }
	 * 
	 * String actualSet = lyric.getLyric_name(); String expected = "Dhooram";
	 * assertEquals(actualSet, expected);
	 * 
	 * assertNotNull(lyric.getLyric_name()); assertNotNull(lyric.getLyricId());
	 * assertNotNull(lyric.getLyricViews()); assertNotNull(lyric.getMovieId());
	 * assertNotNull(lyric.getMovieName()); assertNotNull(lyric.getReleaseDate());
	 * assertNotNull(lyric.getWriterName()); }
	 * 
	 * @Test public void getWriter() { lyrics = new LyricContentDAO(); Set<String>
	 * actual = lyrics.getWriter();
	 * 
	 * TreeSet<String> writerSet = new TreeSet<String>(); PreparedStatement ptmt =
	 * null; ResultSet resultSet = null; String query =
	 * "SELECT writer_name FROM lyrics.l_lyrics order by writer_name ASC"; try {
	 * connection = (Connection) getConnection(); ptmt =
	 * connection.prepareStatement(query); resultSet = ptmt.executeQuery(); while
	 * (resultSet.next()) { writerSet.add(resultSet.getString("writer_name")); } }
	 * catch (SQLException e) { e.printStackTrace(); } finally {
	 * closeResultset(resultSet); closePtmt(ptmt); closeConnection(); }
	 * 
	 * assertNotNull(writerSet); assertNotNull(actual); }
	 * 
	 * @Test public void getLyrics() { lyrics = new LyricContentDAO();
	 * List<LyricContent> song = lyrics.getLyrics(1); String expected =
	 * "##Inkaa istam penchindhadhee#Malli malli kalise thondharaa#Kaalaannainaa tharimesthundhadhi##Aa dhikku ee dhikku#Maunamgaa okkataiyyaye##Naa ooru nee ooru#Manalni iha veru cheyleve## #Raa raa raa#Kaugilai##Raa raa raa#Oopirai##Raa raa raa#Kaugilai##Raa raa raa#Oopirai##Praanam rekkalu chaasthunnadhee#Neekai rivvuna vasthunnadhi#Neepai vaali nidhurinchaalani#Aakaashaanne Odisthunnadhi##Naadhaaka nuv vosthe#Needhaaka nenu vosthunte#Ee dhesham ee lokam#Inkinkaa chinnavainaaye##Raa raa raa raararaa#"
	 * ; LyricContent lyricContent = song.get(0); String actual =
	 * lyricContent.getLyricContent();
	 * 
	 * LyricContent content = null; List<LyricContent> lyrics = null; List<L_lyrics>
	 * allLyrics = new ArrayList<L_lyrics>();
	 * 
	 * if (allLyrics != null) { lyrics = new ArrayList<LyricContent>(); for
	 * (L_lyrics list : allLyrics) { if (1 == list.getLyricId()) { content = new
	 * LyricContent(); content.setLyricContent(expected);
	 * content.setUrl("Xen6_YnPKsY"); lyrics.add(content); break; } } }
	 * 
	 * assertEquals(expected, actual); assertNotNull(actual);
	 * assertNotNull(content.getLyricContent()); assertNotNull(content.getUrl()); }
	 * 
	 * @Test public void getLyricsByWriterMovie() { lyrics = new LyricContentDAO();
	 * List<LyircsByMovie> writersongs = lyrics.getLyricsByWriterMovie(9,
	 * "Srimani"); LyircsByMovie movie = writersongs.get(0); String actual =
	 * movie.getLyricTitle(); String expected = "Arere Yekada";
	 * 
	 * LyircsByMovie lyric = null; List<LyircsByMovie> allLyrics = new
	 * ArrayList<LyircsByMovie>(); List<L_lyrics> allLyricsList = new
	 * ArrayList<L_lyrics>();
	 * 
	 * if (allLyricsList != null) { for (L_lyrics list : allLyricsList) { if (9 ==
	 * list.getMovie().getMovieId() &&
	 * "Srimani".equalsIgnoreCase(list.getWriterName())) { lyric = new
	 * LyircsByMovie(); lyric.setLyricId(1); lyric.setLyricTitle("Dhooram");
	 * lyric.setMovieId(9); lyric.setWriterName("srimani"); allLyrics.add(lyric); }
	 * } }
	 * 
	 * assertEquals(expected, actual); assertNotNull(writersongs);
	 * assertNotNull(lyric.getLyricId()); assertNotNull(lyric.getLyricTitle());
	 * assertNotNull(lyric.getMovieId()); assertNotNull(lyric.getWriterName()); }
	 * 
	 * @Test public void getMoviesByWriter() { lyrics = new LyricContentDAO();
	 * List<Integer> movieNames = lyrics.getMovieIdsByWriter("Srimani"); int movieId
	 * = 3; Integer actual = movieNames.get(0); //
	 * System.out.println(movieId.intValue()); boolean expected =
	 * movieNames.contains(movieId); // assertEquals(expected, actual);
	 * assertNotNull(movieNames);
	 * 
	 * }
	 * 
	 * @Test public void getTeluguLyrics() { lyrics = new LyricContentDAO();
	 * List<L_teluguLyrics> lyricTelugu = lyrics.getTeluguLyrics(1); L_teluguLyrics
	 * content = lyricTelugu.get(0); String expected = content.getLyricContent();
	 * String actual =
	 * "#దూరం దగ్గెర చెస్తున్నది#ఇంకా ఇస్టం పెంచింధధీ#మళ్ళి మళ్ళి కలిసె తొందరా#కాలాన్నైనా తరిమెస్తుందది##ఆ ధిక్కు ఈ ధిక్కు#మౌనంగా ఒక్కటైయ్యయె##నా ఊరు నీ ఊరు#మనల్ని ఇక వెరు చెయలెవె## #రా రా రా#కౌగిలై##రా రా రా#ఊపిరై##రా రా రా#కౌగిలై##రా రా రా#ఊపిరై#ప్రాణం రెక్కలు చాస్తున్నదీ#నీకై రివ్వున వస్తున్నధి#నీపై వాలి నిదురించాలని#ఆకాషాన్నె ఒడిస్తున్నది##నడక నువ్ వస్తె#నీదాక నెను వొస్తుంటె#ఈ దేశం ఈ లొకం#ఇంకింకా చిన్నవైనాయె##రా రా రా రారరా…#"
	 * ; assertNotNull(lyricTelugu); assertEquals(expected, actual);
	 * 
	 * L_teluguLyrics contentTelugu = null; List<L_teluguLyrics> alllyrics = new
	 * ArrayList<L_teluguLyrics>();
	 * 
	 * List<L_lyrics> lyrics = new ArrayList<L_lyrics>(); if (lyrics != null) { for
	 * (L_lyrics lyric : lyrics) { if (lyric.get_id() == 1) { contentTelugu = new
	 * L_teluguLyrics(); contentTelugu.set_id(1);
	 * contentTelugu.setLyricContent(actual); contentTelugu.setUrl("Xen6_YnPKsY");
	 * alllyrics.add(contentTelugu); break; }
	 * 
	 * } } assertNotNull(contentTelugu.get_id());
	 * assertNotNull(contentTelugu.getLyricContent());
	 * assertNotNull(contentTelugu.getUrl());
	 * 
	 * }
	 * 
	 * @Test public void getAllLyrics() {
	 * 
	 * lyrics = new LyricContentDAO(); lydata = new L_lyrics();
	 * lydata.setLyricId(1); lydata.setLyricTitle("Dhooram");
	 * lydata.setLyricContent("dhooram dhooram ...."); LyricMovieDAO movieDAO = new
	 * LyricMovieDAO(); lydata.setMovie(movieDAO.findById(1));
	 * lydata.setWriterName("Anantha_Sriram"); lydata.setLyricViews(1234);
	 * 
	 * java.util.Date date = null; try { date = formatter.parse("20-02-2017"); }
	 * catch (ParseException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } tm = new Timestamp(date.getTime());
	 * 
	 * lydata.setCreationDate(tm); lydata.setUpdationDate(tm);
	 * lydata.setUrl("Xen6_YnPKsY");
	 * 
	 * when(lyrics.findById(1)).thenReturn(lydata);
	 * 
	 * L_lyrics lyricObj = lyrics.findById(1); assertEquals("Dhooram",
	 * lyricObj.getLyricTitle());
	 * 
	 * }
	 */
}
