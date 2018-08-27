package com.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lyrics.dao.BaseDAO;
import com.lyrics.dao.LyricContentDAO;
import com.lyrics.model.Contents;
import com.lyrics.model.Device;
import com.lyrics.model.L_allTimeHits;
import com.lyrics.model.L_lyrics;
import com.lyrics.model.L_teluguLyrics;
import com.lyrics.model.LyircsByMovie;
import com.lyrics.model.LyricContent;
import com.lyrics.model.TrendingMovies;
@CrossOrigin()
@RestController
public class LyricController {

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
	
	@PostMapping(value = "/lyrics/{lyricId}", produces = "application/json")
	@Transactional
	public List<LyricContent> getLyrics(@RequestBody Device device, @PathVariable int lyricId) {
		String deviceId = device.getId();
		List<LyricContent> lyrics = new LyricContentDAO().getLyrics(lyricId, deviceId);
		return lyrics;

	}
	
	@GetMapping(value = "/lyrics/{movieId}/{writerName}", produces = "application/json")
	@Transactional
	public List<LyircsByMovie> findByWriter(@PathVariable int movieId, @PathVariable String writerName) {
		List<LyircsByMovie> moviesByWriter = new LyricContentDAO().getLyricsByWriterMovie(movieId, writerName);

		return moviesByWriter;
	}
	
	@PostMapping(value = "/teluguLyrics/{idTelugu}", produces = "application/json")
	@Transactional
	public List<L_teluguLyrics> findTeluguLyrics(@PathVariable int idTelugu,@RequestBody Device device) {
		String deviceId = device.getId();
		List<L_teluguLyrics> lyrics = new LyricContentDAO().getTeluguLyrics(idTelugu, deviceId);
		return lyrics;
	}
	
	@GetMapping(value = "/allTimeHits", produces = "application/json")
	@Transactional
	public List<L_allTimeHits> getAllTimeHits() {
		List<L_allTimeHits> lyrics = new BaseDAO().findAllTimeHits();
		return lyrics;
	}
	
	@PostMapping(value = "/lyricsUpdate", produces = "application/json")
	@Transactional
	public void updateLyrics(@RequestBody Contents content) {
	
		new LyricContentDAO().updateLyrics(content);
	}
	
	@PostMapping(value = "/teluguLyricsUpdate", produces = "application/json")
	@Transactional
	public void updateTeluguLyrics(@RequestBody Contents content) {
	
		new LyricContentDAO().updateTeluguLyrics(content);
	}
}
