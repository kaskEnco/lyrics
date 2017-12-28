package com.lyrics.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.lyrics.model.L_lyrics;
import com.lyrics.model.L_movie;
import com.lyrics.model.MoviesByWriter;
import com.lyrics.model.MoviesByYear;
import com.lyrics.model.TrendingMovies;

public class LyricContentDAO extends BaseDAO {

	public List<L_lyrics> findAll() {
		List<L_lyrics> lyrics = new ArrayList<L_lyrics>();
		LyricMovieDAO movieDAO = new LyricMovieDAO();
		L_lyrics lyric = null;
		String queryString = "SELECT * FROM l_lyrics ";
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			resultSet = ptmt.executeQuery();
			while (resultSet.next()) {
				lyric = new L_lyrics();
				lyric.setLyricId(resultSet.getInt("id"));
				lyric.setLyricTitle(resultSet.getString("lyric_title"));
				lyric.setLyricContent(resultSet.getString("lyric_content"));
				lyric.setMovie(movieDAO.findById(resultSet.getInt("movie_id")));
				lyric.setWriterName(resultSet.getString("writer_name"));
				lyric.setLyricViews(resultSet.getInt("lyric_views"));
				lyric.setCreationDate(resultSet.getTimestamp("creation_time"));
				lyric.setUpdationDate(resultSet.getTimestamp("updation_time"));
				lyrics.add(lyric);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeResultset(resultSet);
			closePtmt(ptmt);
			closeConnection();
		}

		return lyrics;
	}

	public L_lyrics findById(int Id) {
		L_lyrics lyric = new L_lyrics();
		LyricMovieDAO movieDAO = new LyricMovieDAO();
		String queryString = "SELECT * FROM l_lyrics where id = ?  ";
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setInt(1, Id);
			resultSet = ptmt.executeQuery();
			while (resultSet.next()) {
				lyric.setLyricId(resultSet.getInt("id"));
				lyric.setLyricTitle(resultSet.getString("lyric_title"));
				lyric.setLyricContent(resultSet.getString("lyric_content"));
				lyric.setMovie(movieDAO.findById(resultSet.getInt("movie_id")));
				lyric.setWriterName(resultSet.getString("writer_name"));
				lyric.setLyricViews(resultSet.getInt("lyric_views"));
				lyric.setCreationDate(resultSet.getTimestamp("creation_time"));
				lyric.setUpdationDate(resultSet.getTimestamp("updation_time"));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeResultset(resultSet);
			closePtmt(ptmt);
			closeConnection();
		}

		return lyric;
	}

	public List<L_lyrics> getLyricsByMovie(int movieId) {
		L_lyrics lyric;
		List<L_lyrics> allLyrics = new ArrayList<L_lyrics>();
		String queryString = "Select * from l_lyrics where movie_id = ?";
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setInt(1, movieId);
			resultSet = ptmt.executeQuery();
			while (resultSet.next()) {
				lyric = new L_lyrics();
				lyric.setLyricId(resultSet.getInt("id"));
				lyric.setLyricTitle(resultSet.getString("lyric_title"));
				lyric.setLyricContent(resultSet.getString("lyric_content"));
				lyric.setWriterName(resultSet.getString("writer_name"));
				lyric.setLyricViews(resultSet.getInt("lyric_views"));
				lyric.setCreationDate(resultSet.getTimestamp("creation_time"));
				lyric.setUpdationDate(resultSet.getTimestamp("updation_time"));
				allLyrics.add(lyric);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeResultset(resultSet);
			closePtmt(ptmt);
			closeConnection();
		}

		return allLyrics;

	}

	public List<TrendingMovies> getTrendingLyrics() {
		TrendingMovies lyric;
		List<TrendingMovies> trendingLyrics = new ArrayList<TrendingMovies>();
		LyricMovieDAO movieDAO = new LyricMovieDAO();
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		String queryString = "select * from l_lyrics order by lyric_views DESC limit 15 ";
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			resultSet = ptmt.executeQuery();
			while (resultSet.next()) {
				lyric = new TrendingMovies();
				lyric.setLyric_name(resultSet.getString("lyric_title"));
				L_movie movie = movieDAO.findById(resultSet.getInt("movie_id"));
				lyric.setMovieId(movie.getMovieId());
				lyric.setMovieName(movie.getMovieName());
				lyric.setWriterName(resultSet.getString("writer_name"));
				lyric.setLyricViews(resultSet.getInt("lyric_views"));
				trendingLyrics.add(lyric);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeResultset(resultSet);
			closePtmt(ptmt);
			closeConnection();
		}

		return trendingLyrics;
	}

	public List<MoviesByWriter> getMoviesByWriter() {
		// TODO Auto-generated method stub
		MoviesByWriter movie;
		List<MoviesByWriter> movies = new ArrayList<MoviesByWriter>();
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		String queryString = "SELECT writer_name FROM lyrics.l_lyrics order by writer_name ASC LIMIT 15";
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			resultSet = ptmt.executeQuery();
			while (resultSet.next()) {
				movie = new MoviesByWriter();
				movie.setWriterName(resultSet.getString("writer_name"));
				movies.add(movie);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeResultset(resultSet);
			closePtmt(ptmt);
			closeConnection();
		}
		return movies;
	}

	public Set<String> getWriter() {
		// TODO Auto-generated method stub

		TreeSet<String> writerSet = new TreeSet<String>();
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		String query = "SELECT writer_name FROM lyrics.l_lyrics order by writer_name ASC";
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(query);
			resultSet = ptmt.executeQuery();
			while (resultSet.next()) {
				writerSet.add(resultSet.getString("writer_name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeResultset(resultSet);
			closePtmt(ptmt);
			closeConnection();
		}
		return writerSet;
	}

}
