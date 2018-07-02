package com.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lyrics.dao.LyricContentDAO;
import com.lyrics.dao.LyricMovieDAO;
import com.lyrics.model.MoviesByWriter;

@RestController
public class WriterController {

	@GetMapping(value = "/writer", produces = "application/json")
	@Transactional
	public HashMap<String, Set<String>> findByWriter() {
		HashMap<String, Set<String>> map = new HashMap<>();
		Set<String> writers = new LyricContentDAO().getWriter();
		map.put("writers", writers);
		return map;
	}

	
	@GetMapping(value = "/writer/{writerName}", produces = "application/json")
	@Transactional
	public List<MoviesByWriter> findByWriter(@PathVariable String writerName) {

		List<MoviesByWriter> moviesByWriter = new LyricMovieDAO().getMoviesByWriter(writerName);

		return moviesByWriter;
	}
	
}
