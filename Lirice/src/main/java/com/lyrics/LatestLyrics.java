package com.lyrics;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lyrics.dao.LyricContentDAO;
import com.lyrics.dao.LyricMovieDAO;
import com.lyrics.dao.LyricYearDAO;
import com.lyrics.model.TrendingMovies;
import com.lyrics.model.L_lyrics;
import com.lyrics.model.L_year;
import com.lyrics.model.LyircsByMovie;
import com.lyrics.model.LyricContent;
import com.lyrics.model.MoviesByWriter;
import com.lyrics.model.MoviesByYear;
import com.lyrics.model.MoviesLatest;

@RestController
public class LatestLyrics {

	@GetMapping(value = "/test", produces = "application/json")
	@Transactional
	public TrendingMovies test() {

		TrendingMovies map = new TrendingMovies();
		map.setLyric_name("LyricOne");
		map.setLyricViews(12324);
		map.setMovieName("MovieOne");

		// map.put("key", "value");
		// map.put("foo", "bar");
		// map.put("aa", "bb");

		return map;
	}

	@GetMapping(value = "/latestMovies", produces = "application/json")
	@Transactional
	public List<MoviesLatest> findLatestMovies() {
		List<MoviesLatest> latestMovies = new LyricMovieDAO().findLatest();
		return latestMovies;
	}

	@GetMapping(value = "/latestMoviesAll", produces = "application/json")
	@Transactional
	public List<MoviesLatest> findAllLatestMovies() {
		List<MoviesLatest> latestMovies = new LyricMovieDAO().findAllLatest();
		return latestMovies;
	}

	@GetMapping(value = "/trending", produces = "application/json")
	@Transactional
	public List<TrendingMovies> findTrending() {
		List<TrendingMovies> movies = new LyricContentDAO().getTrendingLyrics();

		return movies;
	}

	@GetMapping(value = "/trendingAll", produces = "application/json")
	@Transactional
	public List<TrendingMovies> findAllTrending() {
		List<TrendingMovies> movies = new LyricContentDAO().getAllTrendingMovies();

		return movies;
	}

	@GetMapping(value = "/years", produces = "application/json")
	@Transactional
	public List<L_year> findYears() {
		List<L_year> years = new LyricYearDAO().findAll();

		return years;
	}

	@GetMapping(value = "/writer", produces = "application/json")
	@Transactional
	public HashMap<String, Set<String>> findByWriter() {
		HashMap<String, Set<String>> map = new HashMap<>();
		Set<String> writers = new LyricContentDAO().getWriter();
		map.put("writers", writers);
		return map;
	}

	@GetMapping(value = "/year/{year}", produces = "application/json")
	@Transactional
	public List<MoviesByYear> findByMovieYear(@PathVariable int year) {
		List<MoviesByYear> years = new LyricMovieDAO().findByYear(year);

		return years;
	}

	@GetMapping(value = "/movies/{movieId}", produces = "application/json")
	@Transactional
	public List<LyircsByMovie> findByMovieId(@PathVariable int movieId) {
		List<LyircsByMovie> lyrics = new LyricContentDAO().getLyricsByMovie(movieId);

		return lyrics;
	}

	@GetMapping(value = "/writer/{writerName}", produces = "application/json")
	@Transactional
	public List<MoviesByWriter> findByWriter(@PathVariable String writerName) {

		List<MoviesByWriter> moviesByWriter = new LyricMovieDAO().getMoviesByWriter(writerName);

		return moviesByWriter;
	}

	@GetMapping(value = "/lyrics/{lyricId}", produces = "application/json")
	@Transactional
	public List<LyricContent> getLyrics(@PathVariable int lyricId) {
		List<LyricContent> lyrics = new LyricContentDAO().getLyrics(lyricId);

		return lyrics;
	}

	@GetMapping(value = "/lyrics/{movieId}/{writerName}", produces = "application/json")
	@Transactional
	public List<LyircsByMovie> findByWriter(@PathVariable int movieId, @PathVariable String writerName) {
		List<LyircsByMovie> moviesByWriter = new LyricContentDAO().getLyricsByWriterMovie(movieId, writerName);

		return moviesByWriter;
	}

}
