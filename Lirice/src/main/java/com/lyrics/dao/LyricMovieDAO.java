package com.lyrics.dao;

import java.io.ByteArrayInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lyrics.AESEncryption;
import com.lyrics.Constants;
import com.lyrics.model.L_movie;
import com.lyrics.model.L_year;
import com.lyrics.model.MoviesByWriter;
import com.lyrics.model.MoviesLatest;

public class LyricMovieDAO extends BaseDAO {

	AESEncryption encry = new AESEncryption();

	public L_movie findById(int id) {
		L_movie movie = new L_movie();
		List<L_movie> movies = findAllMovies();

		if (movies != null) {
			for (L_movie list : movies) {
				if (list.getMovieId() == id) {

					return list;

				}
			}
		}

		return movie;
		
	}

	public String findLatest() {
		List<MoviesLatest> movies = null;
		String movie = null;
		String moviesEnc = null;
		List<MoviesLatest> decMov;
		List<MoviesLatest> moviesLatest = findAllLatest();
		if (moviesLatest != null || moviesLatest.size() > 0) {
			int i = 0;
			movies = new ArrayList<MoviesLatest>();
			for (MoviesLatest list : moviesLatest) {
				if (i < Constants.LATEST_MOVIES_LIMIT) {
					movies.add(list);
					i++;
					continue;
				}
				break;
			}
		}
	System.out.println(movies);
	Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	String json = gson.toJson(movies);
	
		moviesEnc = encry.encrypt(json, "lyricsbykaskenco");
		movie = encry.decrypt(moviesEnc, "lyricsbykaskenco");
		return moviesEnc;
	
		
	}
	

	public List<L_movie> findByYear(L_year year) {
		List<L_movie> moviesByYear = new ArrayList<L_movie>();

		List<L_movie> movieYears = findAllMovies();

		if (movieYears != null) {
			for (L_movie list : movieYears) {
				if (year.getLyircYear() == list.getYear().getLyircYear()) {
					moviesByYear.add(list);
				}
			}
		}
		return moviesByYear;
		
	}

	private int getYear(Timestamp timestamp) {
		
		return 0;
	}

	public List<MoviesByWriter> getMoviesByWriter(String writerName) {
		
		MoviesByWriter movie;
		List<Integer> movieIds = null;
		List<MoviesByWriter> movies = new ArrayList<MoviesByWriter>();
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		movieIds = new LyricContentDAO().getMovieIdsByWriter(writerName);
		if (movieIds != null && movieIds.size() > 0) {
			for (int i = 0; i < movieIds.size(); i++) {

				String queryString = "SELECT movie_name,id,movie_release_date FROM lyrics.l_movie where id = ? ";
				try {
					connection = getConnection();
					ptmt = connection.prepareStatement(queryString);
					ptmt.setInt(1, movieIds.get(i));
					resultSet = ptmt.executeQuery();
					while (resultSet.next()) {
						movie = new MoviesByWriter();
						movie.setMovieId(resultSet.getInt("id"));
						movie.setMovieName(resultSet.getString("movie_name"));
						movie.setWriterName(writerName);
						movie.setMovieReleaseDate(resultSet.getTimestamp("movie_release_date"));
						// movie.setMovieId(movieId);
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
			}

		}

		return movies;
	}

	public List<MoviesLatest> findAllLatest() {

		MoviesLatest latest;
		List<MoviesLatest> moviesLatest = null;

		if (Constants.USE_MEMCACHED) {
			cache = getMemcacheConnection();
			moviesLatest = (List<MoviesLatest>) cache.get(Constants.LATEST_MOVIES_KEY);
		}

		if (moviesLatest != null) {
			return moviesLatest;
		} else {
			moviesLatest = new ArrayList<MoviesLatest>();
			PreparedStatement ptmt = null;
			ResultSet resultSet = null;
			String queryString = "SELECT * FROM lyrics.l_movie ORDER BY movie_release_date DESC";
			try {
				connection = getConnection();
				ptmt = connection.prepareStatement(queryString);
				resultSet = ptmt.executeQuery();
				while (resultSet.next()) {
					latest = new MoviesLatest();
					int movieId = resultSet.getInt("id");
					latest.setMovieId(movieId);
					latest.setMovieName(resultSet.getString("movie_name"));
					latest.setMovieReleaseDate(resultSet.getTimestamp("movie_release_date"));
					moviesLatest.add(latest);
				}
				cache.add(Constants.LATEST_MOVIES_KEY, 0, moviesLatest);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeResultset(resultSet);
				closePtmt(ptmt);
				closeConnection();
			}
		}

		return moviesLatest;

	}
}
