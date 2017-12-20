package com.lyrics;

import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyrics.dao.LyricMovieDAO;
import com.lyrics.dao.LyricYearDAO;
import com.lyrics.model.TrendingMovies;
import com.lyrics.model.L_year;
import com.lyrics.model.MoviesLatest;

@RestController
public class LatestLyrics {
	
	@GetMapping(value = "/latest", produces = "application/json")
	@Transactional
	  public HashMap<String, String> test() {
		
		 HashMap<String, String> map = new HashMap<>();
//		    map.put("key", "value");
//		    map.put("foo", "bar");
//		    map.put("aa", "bb");
		 
		    return map;
	  }
	
	@GetMapping(value = "/years", produces = "application/json")
	@Transactional
	public List<L_year> findAllYears() {
		List<L_year> year = new LyricYearDAO().findAll();
		
		return year;
	}
	
	@GetMapping(value = "/latestMovies", produces = "application/json")
	@Transactional
	public List<MoviesLatest> findLatestMovies() {
		List<MoviesLatest> latestMovies = new LyricMovieDAO().findLatest();
		
		return latestMovies;
	}
	
	
	@GetMapping(value = "/trending", produces = "application/json")
	@Transactional
	public List<TrendingMovies> findTrending() {
		List<TrendingMovies> movies = null;
		
		return movies;
	}
	
	
	
	
	
	

   
	
	
}
