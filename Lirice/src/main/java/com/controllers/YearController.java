package com.controllers;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lyrics.dao.LyricMovieDAO;
import com.lyrics.dao.LyricYearDAO;
import com.lyrics.model.L_movie;
import com.lyrics.model.L_year;


@RestController
public class YearController {

	@GetMapping(value = "/year/{year}", produces = "application/json")
	@Transactional
	public List<L_movie> findByMovieYear(@PathVariable int year) {
		L_year lYear = new LyricYearDAO().findByYear(year);
		List<L_movie> years = new LyricMovieDAO().findByYear(lYear);

		return years;
	}
	
	@GetMapping(value = "/years", produces = "application/json")
	@Transactional
	public List<L_year> findYears() {
		List<L_year> years = new LyricYearDAO().findAllYears();
		return years;
	}
	
}
