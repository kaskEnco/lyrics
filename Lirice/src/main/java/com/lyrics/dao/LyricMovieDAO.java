package com.lyrics.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.lyrics.model.L_movie;
import com.lyrics.model.MoviesByYear;
import com.lyrics.model.MoviesLatest;

public class LyricMovieDAO extends BaseDAO{

	
	public L_movie findALl() {
		L_movie movie = new L_movie();
		LyricLanguageDAO langDAO = new LyricLanguageDAO();
		LyricYearDAO yearDAO = new LyricYearDAO();
		String queryString = "SELECT * FROM l_movie ";
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			resultSet = ptmt.executeQuery();
			while(resultSet.next()) {
				movie.setMovieId(resultSet.getInt("id"));
				movie.setLanguage(langDAO.findById(resultSet.getInt("lang_id")));
				movie.setYear(yearDAO.findById(resultSet.getInt("movie_year_id")));
				movie.setMovieName(resultSet.getString("movie_name"));
				movie.setMovieReleaseDate(resultSet.getTimestamp("movie_release_date"));
				movie.setCreationDate(resultSet.getTimestamp("creation_time"));
				movie.setUpdationDate(resultSet.getTimestamp("updation_time"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return  movie;
	}
	
	public L_movie findById(int id) {
		L_movie movie = new L_movie();
		LyricLanguageDAO langDAO = new LyricLanguageDAO();
		LyricYearDAO yearDAO = new LyricYearDAO();
		String queryString = "SELECT * FROM l_movie WHERE (id = ? )";
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setInt(1, id);
			resultSet = ptmt.executeQuery();
			while(resultSet.next()) {
				movie.setMovieId(resultSet.getInt("id"));
				movie.setLanguage(langDAO.findById(resultSet.getInt("lang_id")));
				movie.setYear(yearDAO.findById(resultSet.getInt("movie_year_id")));
				movie.setMovieName(resultSet.getString("movie_name"));
				movie.setMovieReleaseDate(resultSet.getTimestamp("movie_release_date"));
				movie.setCreationDate(resultSet.getTimestamp("creation_time"));
				movie.setUpdationDate(resultSet.getTimestamp("updation_time"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return  movie;
	}
	
	public List<MoviesLatest> findLatest(){
		MoviesLatest latest;
		List<MoviesLatest> moviesLatest = new ArrayList<MoviesLatest>();
		String queryString = "SELECT * FROM lyrics.l_movie ORDER BY movie_release_date DESC LIMIT 15";
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			resultSet = ptmt.executeQuery();
			while(resultSet.next()){
				latest = new MoviesLatest();
				int movieId = resultSet.getInt("id");
				latest.setMovieId(movieId);
				latest.setMovieName(resultSet.getString("movie_name"));
				latest.setMovieReleaseDate(resultSet.getTimestamp("movie_release_date"));
				moviesLatest.add(latest);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return moviesLatest;
	}
	
	public List <MoviesByYear> findByYear(int yearId){
		MoviesByYear movieYear;
		List<MoviesByYear> movieYears = new ArrayList<MoviesByYear>();
		String queryString = "SELECT * FROM lyrics.l_movie where movie_year_id = ?";
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setInt(1,new LyricYearDAO().findByYear(yearId));
			resultSet = ptmt.executeQuery();
			while(resultSet.next()){
				movieYear = new MoviesByYear();
				int movieId = resultSet.getInt("id");
				movieYear.setMovieId(movieId);
				movieYear.setMovieName(resultSet.getString("movie_name"));
				movieYear.setYear(yearId);
				movieYears.add(movieYear);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return movieYears;
	}

	private int getYear(Timestamp timestamp) {
		// TODO Auto-generated method stub
		return 0;
	}
}
