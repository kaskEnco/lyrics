package com.controllers;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lyrics.dao.LyricContentDAO;
import com.lyrics.dao.LyricMovieDAO;
import com.lyrics.model.LyircsByMovie;
import com.lyrics.model.MoviesLatest;

@RestController
public class MoviesController {

	@GetMapping(value = "/latestMovies", produces = "application/json")
	@Transactional
	public String findLatestMovies() {
		String latestMovies = new LyricMovieDAO().findLatest();
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
	
	@GetMapping(value = "/movies/{movieId}", produces = "application/json")
	@Transactional
	public List<LyircsByMovie> findByMovieId(@PathVariable int movieId) {
		List<LyircsByMovie> lyrics = new LyricContentDAO().getLyricsByMovie(movieId);

		return lyrics;
	}
	
	
}
