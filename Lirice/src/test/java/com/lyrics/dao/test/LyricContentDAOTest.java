package com.lyrics.dao.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;

import com.lyrics.LatestLyrics;
import com.lyrics.dao.LyricContentDAO;
import com.lyrics.model.L_lyrics;
import com.lyrics.model.L_movie;
import com.lyrics.model.LyircsByMovie;
import com.lyrics.model.LyricContent;
import com.lyrics.model.MoviesByWriter;
import com.lyrics.model.TrendingMovies;

class LyricContentDAOTest {

	// MockMvc mockmvc;

	LyricContentDAO lyrics;

	@Test
	void LyricByLyricId() {
		lyrics = new LyricContentDAO();
		String expected = "Dhooram";
		L_lyrics actual = lyrics.findById(1);
		String lyricname = actual.getLyricTitle();
		assertEquals(expected, lyricname);
	}

	/*
	 * @InjectMocks public LyricContentDAO latestlyrics;
	 * 
	 * @Before public void getMock() { mockmvc =
	 * MockMvcBuilders.standaloneSetup(latestlyrics).build(); }
	 */
	@Test
	void LyricByMovieId() {
		lyrics = new LyricContentDAO();
		String expected = "Kannu moosthe badrinath";
		List<LyircsByMovie> actual = lyrics.getLyricsByMovie(400);
		LyircsByMovie i = actual.get(1);
		String song = i.getLyricTitle();
		// System.out.println(song);
		// String movie =
		assertEquals(expected, song);
		/*
		 * //mockmvc.perform(get("/LiriceApp/years")); RequestBuilder requestBuilder =
		 * MockMvcRequestBuilders .get("/years") .accept(MediaType.APPLICATION_JSON);
		 * 
		 * 
		 * MvcResult result = mockmvc.perform(requestBuilder).andReturn();
		 * 
		 * MockHttpServletResponse response = result.getResponse();
		 * 
		 * assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		 * 
		 * assertEquals("http://localhost:8080/LiriceApp/years",
		 * response.getHeader(HttpHeaders.LOCATION));
		 */
	}

	@Test
	public void getTrendingLyrics() {
		lyrics = new LyricContentDAO();
		List<TrendingMovies> actual = lyrics.getTrendingLyrics();
		assertNotNull(actual);
	}

	@Test
	public void getAllTrendingMovies() {
		lyrics = new LyricContentDAO();
		List<TrendingMovies> actual = lyrics.getAllTrendingMovies();
		assertNotNull(actual);
	}

	@Test
	public void getWriter() {
		lyrics = new LyricContentDAO();
		Set<String> actual = lyrics.getWriter();
		assertNotNull(actual);
	}

	@Test
	public void getLyrics() {
		lyrics = new LyricContentDAO();
		List<LyricContent> song = lyrics.getLyrics(1);
		String expected = "##Inkaa istam penchindhadhee#Malli malli kalise thondharaa#Kaalaannainaa tharimesthundhadhi##Aa dhikku ee dhikku#Maunamgaa okkataiyyaye##Naa ooru nee ooru#Manalni iha veru cheyleve## #Raa raa raa#Kaugilai##Raa raa raa#Oopirai##Raa raa raa#Kaugilai##Raa raa raa#Oopirai##Praanam rekkalu chaasthunnadhee#Neekai rivvuna vasthunnadhi#Neepai vaali nidhurinchaalani#Aakaashaanne Odisthunnadhi##Naadhaaka nuv vosthe#Needhaaka nenu vosthunte#Ee dhesham ee lokam#Inkinkaa chinnavainaaye##Raa raa raa raararaa#";
		LyricContent content = song.get(0);
		String actual = content.getLyricContent();
		assertEquals(expected, actual);
		assertNotNull(actual);
	}

	@Test
	public void getLyricsByWriterMovie() {
		lyrics = new LyricContentDAO();
		List<LyircsByMovie> writersongs = lyrics.getLyricsByWriterMovie(9, "Srimani");
		LyircsByMovie movie = writersongs.get(0);
		String actual = movie.getLyricTitle();
		String expected = "Arere Yekada";
		assertEquals(expected, actual);
		assertNotNull(writersongs);
	}

	@Test
	public void getMoviesByWriter() {
		lyrics = new LyricContentDAO();
		List<Integer> movieNames = lyrics.getMovieIdsByWriter("Srimani");
		int movieId = 3;
		Integer actual = movieNames.get(0);
		// System.out.println(movieId.intValue());
		boolean expected = movieNames.contains(movieId);
		// assertEquals(expected, actual);
		assertNotNull(movieNames);
	}

	@Test
	public void getTeluguLyrics() {
		lyrics = new LyricContentDAO();
		List<LyricContent> lyricTelugu = lyrics.getTeluguLyrics(1);
		LyricContent content = lyricTelugu.get(0);
		String expected = content.getLyricContent();
		String actual = "#దూరం దగ్గెర చెస్తున్నది#ఇంకా ఇస్టం పెంచింధధీ#మళ్ళి మళ్ళి కలిసె తొందరా#కాలాన్నైనా తరిమెస్తుందది##ఆ ధిక్కు ఈ ధిక్కు#మౌనంగా ఒక్కటైయ్యయె##నా ఊరు నీ ఊరు#మనల్ని ఇక వెరు చెయలెవె## #రా రా రా#కౌగిలై##రా రా రా#ఊపిరై##రా రా రా#కౌగిలై##రా రా రా#ఊపిరై#ప్రాణం రెక్కలు చాస్తున్నదీ#నీకై రివ్వున వస్తున్నధి#నీపై వాలి నిదురించాలని#ఆకాషాన్నె ఒడిస్తున్నది##నడక నువ్ వస్తె#నీదాక నెను వొస్తుంటె#ఈ దేశం ఈ లొకం#ఇంకింకా చిన్నవైనాయె##రా రా రా రారరా…#";
		assertNotNull(lyricTelugu);
		assertEquals(expected, actual);
	}
	
	
}
