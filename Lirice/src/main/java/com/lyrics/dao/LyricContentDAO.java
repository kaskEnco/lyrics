package com.lyrics.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.json.simple.JSONObject;

import com.lyrics.Constants;
import com.lyrics.model.L_language;
import com.lyrics.model.L_lyrics;
import com.lyrics.model.L_movie;
import com.lyrics.model.L_teluguLyrics;
import com.lyrics.model.LyircsByMovie;
import com.lyrics.model.LyricContent;
import com.lyrics.model.TrendingMovies;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class LyricContentDAO extends BaseDAO {

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

			e.printStackTrace();
		} finally {
			closeResultset(resultSet);
			closePtmt(ptmt);
			closeConnection();
		}

		return lyric;
	}

	public List<LyircsByMovie> getLyricsByMovie(int movieId) {
		LyircsByMovie lyric;
		List<LyircsByMovie> allLyrics = null;

		List<L_lyrics> moviesList = findAllLyrics();
		if (moviesList != null) {
			allLyrics = new ArrayList<LyircsByMovie>();
			for (L_lyrics list : moviesList) {
				if (movieId == list.getMovie().getMovieId()) {
					lyric = new LyircsByMovie();
					lyric.setLyricId(list.getLyricId());
					lyric.setLyricTitle(list.getLyricTitle());
					lyric.setMovieId(list.getMovie().getMovieId());
					lyric.setWriterName(list.getWriterName());
					allLyrics.add(lyric);
				}
			}
		}

		return allLyrics;
		
	}

	public List<TrendingMovies> getTrendingLyrics() {
		List<TrendingMovies> lyrics = null;
		List<TrendingMovies> trendingLyrics = getAllTrendingMovies();

		if (trendingLyrics != null) {
			int i = 0;
			lyrics = new ArrayList<TrendingMovies>();
			for (TrendingMovies list : trendingLyrics) {
				if (i < Constants.LATEST_TRENDING_LIMIT) {
					lyrics.add(list);
					i++;
					continue;
				}
				break;
			}
		}
		return lyrics;

	
	}

	public Set<String> getWriter() {

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

			e.printStackTrace();
		} finally {
			closeResultset(resultSet);
			closePtmt(ptmt);
			closeConnection();
		}
		return writerSet;
	}

	public List<LyricContent> getLyrics(int lyricId, String device) {

		/*
		 * PreparedStatement ptmt = null; ResultSet resultSet = null; String queryString
		 * = "SELECT id FROM l_session where device_name = ? "; int deviceId = 0; try {
		 * 
		 * connection = getConnection(); ptmt =
		 * connection.prepareStatement(queryString); ptmt.setString(1, device);
		 * resultSet = ptmt.executeQuery(); while(resultSet.next()) { deviceId =
		 * resultSet.getInt("id"); }
		 * 
		 * } catch (SQLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } String query =
		 * "select * from l_ids where device_id = ? and lyric_id = ?"; try {
		 * 
		 * connection = getConnection(); ptmt = connection.prepareStatement(query);
		 * ptmt.setInt(1, deviceId); ptmt.setInt(2, lyricId); resultSet =
		 * ptmt.executeQuery();
		 * 
		 * 
		 * if(resultSet.next() == false) { String queryAdd =
		 * "insert into l_ids values (null, ?, ?)"; connection = getConnection(); ptmt =
		 * connection.prepareStatement(queryAdd); ptmt.setInt(1, lyricId);
		 * ptmt.setInt(2, deviceId); ptmt.execute();
		 * 
		 * String queryCount =
		 * "update lyrics.l_lyrics set lyric_views = lyric_views+1 where id = ?"; ptmt =
		 * connection.prepareStatement(queryCount); ptmt.setInt(1, lyricId);
		 * ptmt.executeUpdate(); }
		 * 
		 * } catch (SQLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		String queryString = "SELECT ids FROM l_session where device_name = ? ";
		String ids = null;
		boolean idExist = false;
		List<String> idsList = null;
		ArrayList<String> updatableList = new ArrayList<String>();
		String[] stringArray = null;
		try {

			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setString(1, device);
			resultSet = ptmt.executeQuery();
			while (resultSet.next()) {
				ids = resultSet.getString("ids");
			}
			
			if (ids != null) {
				String[] arrayOfIds = ids.split("\\s*,\\s*");
				idsList = Arrays.asList(arrayOfIds);
				idExist = idsList.stream().anyMatch(id -> id.equals(lyricId));
				updatableList.addAll(idsList);
			}

			if (!idExist) {
				updatableList.add(Integer.toString(lyricId));
				stringArray = updatableList.toArray(new String[updatableList.size()]);
				String str = String.join(",", stringArray);
				String query = "update l_session set ids = ? where device_name = ? ";
				connection = getConnection();
				ptmt = connection.prepareStatement(query);
				ptmt.setString(1, str);
				ptmt.setString(2, device);
				ptmt.executeUpdate();

				String queryCount = "update lyrics.l_lyrics set lyric_views = lyric_views+1 where id = ?";
				ptmt = connection.prepareStatement(queryCount);
				ptmt.setInt(1, lyricId);
				ptmt.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LyricContent content;
		List<LyricContent> lyrics = null;
		List<L_lyrics> allLyrics = findAllLyrics();

		if (allLyrics != null) {

			lyrics = new ArrayList<LyricContent>();
			for (L_lyrics list : allLyrics) {
				if (lyricId == list.getLyricId()) {
					content = new LyricContent();
					content.setLyricContent(list.getLyricContent());
					content.setUrl(list.getUrl());
					lyrics.add(content);
					break;
				}
			}
		}
		return lyrics;
	}

	public List<TrendingMovies> getAllTrendingMovies() {
		List<TrendingMovies> trendingLyrics = null;
		
		if (Constants.USE_MEMCACHED) {
			cache = getMemcacheConnection();
			trendingLyrics = (List<TrendingMovies>) cache.get(Constants.LATEST_TRENDING_KEY);
		}

		if (trendingLyrics != null)
			return trendingLyrics;

		TrendingMovies lyric;
		trendingLyrics = new ArrayList<TrendingMovies>();
		LyricMovieDAO movieDAO = new LyricMovieDAO();
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		String queryString = "select * from l_lyrics order by lyric_views DESC ";
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			resultSet = ptmt.executeQuery();
			while (resultSet.next()) {
				lyric = new TrendingMovies();
				lyric.setLyricId(resultSet.getInt("id"));
				lyric.setLyric_name(resultSet.getString("lyric_title"));
				L_movie movie = movieDAO.findById(resultSet.getInt("movie_id"));
				lyric.setMovieId(movie.getMovieId());
				lyric.setMovieName(movie.getMovieName());
				lyric.setReleaseDate(movie.getMovieReleaseDate());
				lyric.setWriterName(resultSet.getString("writer_name"));
				lyric.setLyricViews(resultSet.getInt("lyric_views"));

				trendingLyrics.add(lyric);
			}
			cache.add(Constants.LATEST_TRENDING_KEY, 0, trendingLyrics);
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			closeResultset(resultSet);
			closePtmt(ptmt);
			closeConnection();
		}

		return trendingLyrics;
	}

	public List<LyircsByMovie> getLyricsByWriterMovie(int movieId, String writerName) {
		LyircsByMovie lyric;
		List<LyircsByMovie> allLyrics = new ArrayList<LyircsByMovie>();
		List<L_lyrics> allLyricsList = findAllLyrics();

		if (allLyricsList != null) {
			for (L_lyrics list : allLyricsList) {
				if (movieId == list.getMovie().getMovieId() && writerName.equalsIgnoreCase(list.getWriterName())) {
					lyric = new LyircsByMovie();
					lyric.setLyricId(list.getLyricId());
					lyric.setLyricTitle(list.getLyricTitle());
					lyric.setMovieId(movieId);
					lyric.setWriterName(writerName);
					allLyrics.add(lyric);
				}
			}
		}
		return allLyrics;
	
	}

	public List<Integer> getMovieIdsByWriter(String writerName) {

		// int movieId;
		List<Integer> movies = new ArrayList<Integer>();
		List<L_lyrics> lyrics = findAllLyrics();

		if (lyrics != null) {
			for (L_lyrics list : lyrics) {
				if (list.getWriterName().equalsIgnoreCase(writerName)) {
					movies.add(list.getMovie().getMovieId());
				}
			}
		}
		return movies;
	

	}

	public List<L_teluguLyrics> getTeluguLyrics(int idTelugu) {
		L_teluguLyrics content;
		List<L_teluguLyrics> alllyrics = new ArrayList<L_teluguLyrics>();

		List<L_lyrics> lyrics = findAllTeluguLyrics();
		if (lyrics != null) {
			for (L_lyrics lyric : lyrics) {
				if (lyric.get_id() == idTelugu) {
					content = new L_teluguLyrics();
					content.set_id(lyric.get_id());
					content.setLyricContent(lyric.getLyricContent());
					content.setUrl(lyric.getUrl());
					alllyrics.add(content);
					break;
				}

			}
		}

		return alllyrics;
	}

}
