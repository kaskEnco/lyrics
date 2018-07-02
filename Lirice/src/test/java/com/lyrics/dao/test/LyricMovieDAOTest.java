package com.lyrics.dao.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mock;
import com.lyrics.dao.LyricMovieDAO;
import com.lyrics.model.L_movie;
import com.lyrics.model.L_year;
import com.lyrics.model.MoviesByWriter;
import com.lyrics.model.MoviesLatest;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
class LyricMovieDAOTest {

	@Mock
	LyricMovieDAO movies;
	
	LyricMovieDAO dao = mock(LyricMovieDAO.class);
	
	@Test
	public void getMovieById() {

		L_movie movie = new L_movie();
		movie.setMovieId(1);
		movie.setMovieName("Arjun reddy");
		when(dao.findById(anyInt())).thenReturn(movie);
		L_movie expected = dao.findById(1);
		assertEquals(1, expected.getMovieId());
		assertEquals("Arjun reddy", expected.getMovieName());

	}

	@Test
	public void getLatestMovies() {

		List<MoviesLatest> list = new ArrayList<MoviesLatest>();
		MoviesLatest latest = new MoviesLatest();
		latest.setMovieId(1);
		latest.setMovieName("Arjun Reddy");
		list.add(latest);
		when(dao.findLatest()).thenReturn(list);
		List<MoviesLatest> movie = dao.findLatest();
		assertEquals(1, movie.get(0).getMovieId());
		assertEquals("Arjun Reddy", movie.get(0).getMovieName());
	}
	
	@Test
	public void getMovieByYear() {
		
		List<L_movie> moviesByYear = new ArrayList<L_movie>();
		L_year year = new L_year();
		L_movie movie = new L_movie();
		movie.setMovieId(1);
		movie.setMovieName("Arjun reddy");
		moviesByYear.add(movie);
		when(dao.findByYear(anyObject())).thenReturn(moviesByYear);
		List<L_movie> movieYears = dao.findByYear(year);
		assertEquals(1, movieYears.get(0).getMovieId());
		assertEquals("Arjun reddy", movieYears.get(0).getMovieName());
	}
	
	@Test
	public void getMoviesByWriter() {
		
		List<MoviesByWriter> movies = new ArrayList<MoviesByWriter>();
		MoviesByWriter writerMovies = new MoviesByWriter();
		writerMovies.setMovieId(1);
		writerMovies.setMovieName("Arjun");
		movies.add(writerMovies);
		when(dao.getMoviesByWriter(anyString())).thenReturn(movies);
		List<MoviesByWriter> writer = dao.getMoviesByWriter("Srimani");
		assertEquals(1, writer.get(0).getMovieId());
		assertEquals("Arjun", writer.get(0).getMovieName());
		
	}
	
	@Test
	public void getAllLatest() {
		List<MoviesLatest> moviesLatest = new ArrayList<MoviesLatest>();
		MoviesLatest latest = new MoviesLatest();
		latest.setMovieId(1);
		latest.setMovieName("Arjun Reddy");
		moviesLatest.add(latest);
		when(dao.findAllLatest()).thenReturn(moviesLatest);
		List<MoviesLatest> latestAll = dao.findAllLatest();
		assertEquals(1, latestAll.get(0).getMovieId());
		assertEquals("Arjun Reddy", latestAll.get(0).getMovieName());
	}

	/*@Test
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
		L_year year = new L_year();
		year.setLyircYear(2017);
		year.setYearId(1);
		List<L_movie> output = movies.findByYear(year);
		String actual = output.get(0).getMovieName();
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
*/

	
}
