package com.lyrics;


import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.solr.common.SolrDocument;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.lyrics.dao.BaseDAO;
import com.lyrics.dao.LyricContentDAO;
import com.lyrics.dao.LyricMovieDAO;
import com.lyrics.dao.LyricYearDAO;
import com.lyrics.dao.SendingMailDAO;
import com.lyrics.model.TrendingMovies;
import com.lyrics.model.L_movie;
import com.lyrics.model.L_year;
import com.lyrics.model.LyircsByMovie;
import com.lyrics.model.LyricContent;
import com.lyrics.model.Mail;
import com.lyrics.model.MoviesByWriter;
import com.lyrics.model.MoviesLatest;

import com.lyrics.dao.SolrSearchDAO;

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

	@PostMapping(value = "/mail",  consumes = "application/json")
	public String MailDescription(@RequestBody Mail mail){
		String mailSent=new SendingMailDAO().sendMial(mail);

		return "success";
	
	}
	
	

	@GetMapping(value = "/search/{Searchvalue}", produces = "application/json")
	public List<SolrDocument> findSearch(@PathVariable String Searchvalue) {
		/*
		 * HttpSession session = null; HttpServletRequest request = null;
		 * sessionInfo(session , request);
		 */
		List<SolrDocument> searchOp = new SolrSearchDAO().findSearch(Searchvalue);
		return searchOp;
	}

	@GetMapping(value = "/teluguLyrics/{idTelugu}", produces = "application/json")
	@Transactional
	public List<LyricContent> findTeluguLyrics(@PathVariable int idTelugu, HttpServletRequest request) {
		List<LyricContent> lyrics = new LyricContentDAO().getTeluguLyrics(idTelugu);
		
		return lyrics;
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
		long start_time = System.currentTimeMillis();
		List<MoviesLatest> latestMovies = new LyricMovieDAO().findAllLatest();
		long end_time = System.currentTimeMillis();
		System.out.println("Executed in " + (end_time - start_time) + " milliseconds");
		return latestMovies;
	}

	@GetMapping(value = "/trending", produces = "application/json")
	@Transactional
	public List<TrendingMovies> findTrending(HttpSession session, HttpServletRequest request) {

		List<TrendingMovies> movies = new LyricContentDAO().getTrendingLyrics();
		/*
		 * final String sessionIds = request1.getSession().getId(); String ip =
		 * request1.getRemoteAddr();
		 */
		/*
		 * HttpSession session = null; HttpServletRequest request = null;
		 */
		// sessionInfo1( session, request);
		// movies.add((TrendingMovies) session);
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
		List<L_year> years = new LyricYearDAO().findAllYears();
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
	public List<L_movie> findByMovieYear(@PathVariable int year) {
		L_year lYear = new LyricYearDAO().findByYear(year);
		List<L_movie> years = new LyricMovieDAO().findByYear(lYear);

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
	public List<LyricContent> getLyrics(@PathVariable int lyricId, HttpSession session, HttpServletRequest request) {
		List<LyricContent> lyrics = new LyricContentDAO().getLyrics(lyricId);
		//sessionInfo(session, request);
		Object id = session.getAttribute("id" + lyricId);

		if (id == null) {
			BaseDAO base = new BaseDAO();
			base.Count(lyricId);
			session.setAttribute("id" + lyricId, lyricId);
		}

		return lyrics;
	}

	@GetMapping(value = "/lyrics/{movieId}/{writerName}", produces = "application/json")
	@Transactional
	public List<LyircsByMovie> findByWriter(@PathVariable int movieId, @PathVariable String writerName) {
		List<LyircsByMovie> moviesByWriter = new LyricContentDAO().getLyricsByWriterMovie(movieId, writerName);

		return moviesByWriter;
	}
}
