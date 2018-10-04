package com.lyrics.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.simple.JSONObject;

import com.lyrics.Constants;
import com.lyrics.model.Contents;
import com.lyrics.model.L_allTimeHits;
import com.lyrics.model.L_language;
import com.lyrics.model.L_lyrics;
import com.lyrics.model.L_movie;
import com.lyrics.model.L_teluguLyrics;
import com.lyrics.model.LyircsByMovie;
import com.lyrics.model.LyricContent;
import com.lyrics.model.MoviesByWriter;
import com.lyrics.model.TrendingMovies;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import net.spy.memcached.MemcachedClient;

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
		if (Constants.USE_MEMCACHED) {
			cache = getMemcacheConnection();
			allLyrics = (List<LyircsByMovie>) cache.get("movie"+movieId);
		}

		if (allLyrics != null)
			return allLyrics;
		
		String queryString = "SELECT * FROM l_lyrics where movie_id = ? ";
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		allLyrics = new ArrayList<LyircsByMovie>();
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setInt(1, movieId);
			resultSet = ptmt.executeQuery();
			while (resultSet.next()) {
				lyric = new LyircsByMovie();
				lyric.setLyricId(resultSet.getInt("id"));
				lyric.setLyricTitle(resultSet.getString("lyric_title"));
				lyric.setWriterName(resultSet.getString("writer_name"));
				lyric.setMovieId(resultSet.getInt("movie_id"));
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
		/*List<L_lyrics> moviesList = findAllLyrics();
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
		}*/
		cache.add("movie"+movieId, 0, allLyrics);
		return allLyrics;

	}

	public List<TrendingMovies> getTrendingLyrics() {
		List<TrendingMovies> lyrics = null;

		MemcachedClient cache;

		if (Constants.USE_MEMCACHED) {
			cache = getMemcacheConnection();
			lyrics = (List<TrendingMovies>) cache.get(Constants.LATEST_TRENDING);
		}

		if (lyrics != null)
			return lyrics;

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
		cache.add(Constants.LATEST_TRENDING, 0, lyrics);
		return lyrics;
	}

	public Set<String> getWriter() {

		LinkedHashSet<String> writerSet ;
		
		if (Constants.USE_MEMCACHED) {
			cache = getMemcacheConnection();
			writerSet = (LinkedHashSet<String>) cache.get(Constants.WRITERS_KEY);
		}
		if(writerSet != null)
			return writerSet;
		
		writerSet = new LinkedHashSet<String>();
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		String query = "SELECT writer_name FROM lyrics.l_lyrics order by rand()";
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
		cache.add(Constants.WRITERS_KEY, 0, writerSet);
		return writerSet;
	}

	public List<LyricContent> getLyrics(int lyricId, String device) {
		lyricsCount(device, lyricId);
		MemcachedClient cache;
		LyricContent content;
		List<LyricContent> lyrics = null;

		if (Constants.USE_MEMCACHED) {
			cache = getMemcacheConnection();
			lyrics = (List<LyricContent>) cache.get("englishLyric" + lyricId);
		}

		if(lyrics!=null)
			return lyrics;
					
		/*
		 * List<L_lyrics> allLyrics = findAllLyrics();
		 * 
		 * if (allLyrics != null) {
		 * 
		 * lyrics = new ArrayList<LyricContent>(); for (L_lyrics list : allLyrics) { if
		 * (lyricId == list.getLyricId()) { content = new LyricContent();
		 * content.setLyricContent(list.getLyricContent());
		 * content.setUrl(list.getUrl()); lyrics.add(content); break; } } }
		 */
		String queryString = "SELECT * FROM lyrics.l_lyrics where id=?";
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setInt(1, lyricId);
			resultSet = ptmt.executeQuery();
			while (resultSet.next()) {
				lyrics = new ArrayList<LyricContent>();
				content = new LyricContent();
				content.setLyricContent(resultSet.getString("lyric_content"));
				content.setUrl(resultSet.getString("url"));
				lyrics.add(content);
				break;
			}

			cache.add("englishLyric" + lyricId, 0, lyrics);
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
		String queryString = "select * from l_lyrics order by lyric_views DESC limit 100 ";
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
		List<L_lyrics> allLyricsList = findAllLyricsByWriter(writerName);

		if (allLyricsList != null) {
			for (L_lyrics list : allLyricsList) {
				if (movieId == list.getMovie().getMovieId()) {
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
		List<Integer> movie = new ArrayList<Integer>();
		/*List<L_lyrics> lyrics = findAllLyrics();

		if (lyrics != null) {
			for (L_lyrics list : lyrics) {
				if (list.getWriterName().equalsIgnoreCase(writerName)) {
					movies.add(list.getMovie().getMovieId());
				}
			}
		}*/
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		String queryString = "SELECT movie_id FROM lyrics.l_lyrics where writer_name = ? ";
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setString(1, writerName);
			resultSet = ptmt.executeQuery();
			while (resultSet.next()) {
				movie.add(resultSet.getInt("movie_id"));
				// movie.setMovieId(movieId);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeResultset(resultSet);
			closePtmt(ptmt);
			closeConnection();
		}
		return movie;

	}

	public List<L_teluguLyrics> getTeluguLyrics(int idTelugu, String deviceId) {
		L_teluguLyrics content;
		lyricsCount(deviceId, idTelugu);
		MemcachedClient cache;
		List<L_teluguLyrics> alllyrics;

		if (Constants.USE_MEMCACHED) {
			cache = getMemcacheConnection();
			alllyrics = (List<L_teluguLyrics>) cache.get("teluguLyric" + idTelugu);
		}
		if (alllyrics != null)
			return alllyrics;
		/*
		 * List<L_lyrics> lyrics = findAllTeluguLyrics(); if (lyrics != null) { for
		 * (L_lyrics lyric : lyrics) { if (lyric.get_id() == idTelugu) { content = new
		 * L_teluguLyrics(); content.set_id(lyric.get_id());
		 * content.setLyricContent(lyric.getLyricContent());
		 * content.setUrl(lyric.getUrl()); alllyrics.add(content); break; }
		 * 
		 * } }
		 */
		try {
		DB db = getMongoConnection();

		DBCollection collection = db.getCollection("teluguLyrics");
		DBCursor cursor;

		int n = collection.find().count();
		Object value = null;
		// for (int i = 0; i < n; i++) {
		cache = getMemcacheConnection();
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", idTelugu);
		cursor = collection.find(whereQuery);
		value = cursor.next();
		JSONObject obj = new JSONObject((Map) value);
		content = new L_teluguLyrics();
		content.set_id((int) obj.get("_id"));
		content.setLyricContent((String) obj.get("lyricContent"));
		content.setUrl((String) obj.get("url"));
		alllyrics = new ArrayList<L_teluguLyrics>();
		alllyrics.add(content);
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
		cache.add("teluguLyric" + idTelugu, 0, alllyrics);
		// }

		return alllyrics;
	}

	private void lyricsCount(String deviceId, int idTelugu) {
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
			ptmt.setString(1, deviceId);
			resultSet = ptmt.executeQuery();
			while (resultSet.next()) {
				ids = resultSet.getString("ids");
			}

			if (ids != null) {
				String[] arrayOfIds = ids.split("\\s*,\\s*");
				idsList = Arrays.asList(arrayOfIds);
				String songId = Integer.toString(idTelugu);
				idExist = idsList.stream().anyMatch(id -> id.equals(songId));
				updatableList.addAll(idsList);
			}

			if (!idExist) {
				updatableList.add(Integer.toString(idTelugu));
				stringArray = updatableList.toArray(new String[updatableList.size()]);
				String str = String.join(",", stringArray);
				String query = "update l_session set ids = ? where device_name = ? ";
				// connection = getConnection();
				ptmt = connection.prepareStatement(query);
				ptmt.setString(1, str);
				ptmt.setString(2, deviceId);
				ptmt.executeUpdate();

				String queryCount = "update lyrics.l_lyrics set lyric_views = lyric_views+1 where id = ?";
				ptmt = connection.prepareStatement(queryCount);
				ptmt.setInt(1, idTelugu);
				ptmt.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeResultset(resultSet);
			closePtmt(ptmt);
			closeConnection();
		}
	}

	public void updateLyrics(Contents content) {
		// TODO Auto-generated method stub
		String name = content.getName();
		String lyric = content.getContent();
		int id = content.getId();
		PreparedStatement ptmt = null;
		String queryString = "update l_lyrics set lyric_content = ? where id = ?";
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setString(1, lyric);
			ptmt.setInt(2, id);
			ptmt.executeUpdate();
			ptmt.close();
			connection.close();

		} catch (Exception e) {
		} finally {
			closePtmt(ptmt);
			closeConnection();
		}

	}

	public void updateTeluguLyrics(Contents content) {

		MongoClient mongo = new MongoClient("localhost", 27017);
		DB db = mongo.getDB("lyrics");
		DBCollection col = db.getCollection("teluguLyrics");
		DBObject query = new BasicDBObject("_id", content.getId());
		DBObject update = new BasicDBObject();
		update.put("$set", new BasicDBObject("lyricContent", content.getContent()));
		WriteResult result = col.update(query, update);
		mongo.close();

	}

}
