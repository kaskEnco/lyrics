package com.lyrics.dao.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.lyrics.dao.LyricMovieDAO;
import com.lyrics.model.L_movie;
import com.lyrics.model.MoviesByWriter;
import com.lyrics.model.MoviesLatest;

class LyricMovieDAOTest {

	LyricMovieDAO movies;

	@Test
	void movieById() {
		movies = new LyricMovieDAO();
		L_movie output = movies.findById(1);
		String actual = output.getMovieName();
		String expected = "Arjun Reddy";
		assertEquals(expected, actual);
		assertNotNull(output);
	}

	@Test
	void moviesLatest() {
		movies = new LyricMovieDAO();
		List<MoviesLatest> output = movies.findAllLatest();
		MoviesLatest movieDetails = output.get(0);
		String actual = movieDetails.getMovieName();
		String expected = "Hello";
		assertEquals(expected, actual);
		assertNotNull(output);
	}
	
	@Test
	void moviesByYear() {
		movies = new LyricMovieDAO();
		L_movie output = movies.findById(1);
		String actual = output.getMovieName();
		String expected = "Arjun Reddy";
		assertEquals(expected, actual);
		assertNotNull(output);
	}
	
	@Test
	void moviesByWriter() {
		movies = new LyricMovieDAO();
		List<MoviesByWriter> output = movies.getMoviesByWriter("Srimani");
		MoviesByWriter movieDetails = output.get(3);
		String actual = movieDetails.getMovieName();
		String expected = "Nenu Local";
		assertEquals(expected, actual);
		assertNotNull(output);
	}
	
	@Test
	void moviesLatestAll() {
		movies = new LyricMovieDAO();
		List<MoviesLatest> output = movies.findAllLatest();
		MoviesLatest movieDetails = output.get(0);
		String actual = movieDetails.getMovieName();
		String expected = "Hello";
		assertEquals(expected, actual);
		assertNotNull(output);
	}
}
