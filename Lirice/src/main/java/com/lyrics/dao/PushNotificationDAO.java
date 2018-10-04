package com.lyrics.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lyrics.Constants;
import com.lyrics.model.L_allTimeHits;
import com.lyrics.model.L_movie;
import com.lyrics.model.NewSongs;
import com.lyrics.model.NotificationData;
import com.lyrics.model.NotificationRequestModel;

public class PushNotificationDAO extends BaseDAO {

	public List<NewSongs> getNewSongs() {

		List<NewSongs> newsongs;

		if (Constants.USE_MEMCACHED) {
			cache = getMemcacheConnection();
			newsongs = (List<NewSongs>) cache.get(Constants.NEWLY_ADDED_SONGS);
		}

		if (newsongs != null)
			return newsongs;

		newsongs = new ArrayList<NewSongs>();
		NewSongs songs;
		Timestamp lastupdatedtime = null;
		LyricMovieDAO movieDAO = new LyricMovieDAO();
		File file = new File("/tmp/creationtime.txt"); // ("/tmp/creationtime.txt") in aws
		BufferedReader br;
		String year = null;
		PrintWriter pw;
		try {
			br = new BufferedReader(new FileReader(file));
			year = br.readLine();
			pw = new PrintWriter("/tmp/creationtime.txt");
			pw.close();
		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		// String queryString = "SELECT * FROM lyrics.l_lyrics where date(creation_time)
		// >=(select date((SELECT max(creation_time) FROM l_lyrics))) order by
		// creation_time desc";
		String queryString = "SELECT * FROM lyrics.l_lyrics where creation_time > ?";
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setString(1, year);
			resultSet = ptmt.executeQuery();
			while (resultSet.next()) {
				songs = new NewSongs();
				songs.setId(resultSet.getInt("id"));
				songs.setLyricname(resultSet.getString("lyric_title"));
				L_movie movie = movieDAO.findById(resultSet.getInt("movie_id"));
				songs.setMovieId(movie.getMovieId());
				songs.setMoviename(movie.getMovieName());
				songs.setMovieReleaseDate(movie.getMovieReleaseDate());
				songs.setWriterName(resultSet.getString("writer_name"));
				newsongs.add(songs);
			}
			cache.add(Constants.NEWLY_ADDED_SONGS, 0, newsongs);

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			closeResultset(resultSet);
			closePtmt(ptmt);
			closeConnection();
		}
		try {
			String queryStringUpdateTime = "SELECT max(creation_time) FROM l_lyrics";
			connection = getConnection();
			PreparedStatement ptmtupdate = connection.prepareStatement(queryStringUpdateTime);// SELECT
																								// max(creation_time)
																								// FROM l_lyrics
			resultSet = ptmtupdate.executeQuery();
			while (resultSet.next()) {
				lastupdatedtime = resultSet.getTimestamp("max(creation_time)");
			}
			FileWriter writer = new FileWriter("/tmp/creationtime.txt", true);
			writer.write(lastupdatedtime.toString());
			writer.close();
		} catch (SQLException | IOException e) {

			e.printStackTrace();
		} finally {
			closeResultset(resultSet);
			closePtmt(ptmt);
			closeConnection();
		}
		return newsongs;
	}

	public String sendPushNotification() {

		File file = new File("/tmp/creationtime.txt"); // ("/tmp/count.txt") in aws
		BufferedReader br;
		String year = null;
		try {
			br = new BufferedReader(new FileReader(file));
			year = br.readLine();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String queryString = "SELECT lyric_title FROM lyrics.l_lyrics where creation_time > ? limit 4";
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		StringBuilder lyricTitles = new StringBuilder();
		String result = null;
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setString(1, year);
			resultSet = ptmt.executeQuery();
			if (resultSet.next()) {
				while (resultSet.next()) {
					lyricTitles.append(resultSet.getString("lyric_title") + ",");
				}
				result = new BaseDAO().notifications(lyricTitles, "Newly added songs");
			}
			URL url = new URL("http://kasksolutions:90/LiriceApp/newSongs");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br1 = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			//System.out.println("Output from Server .... \n");
			while ((output = br1.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeResultset(resultSet);
			closePtmt(ptmt);
			closeConnection();
		}

		return result;
	}

	public String sendPushNotificationForAllTimeHits() {

		String queryString = "SELECT lyric_title FROM lyrics.l_lyrics where all_time_hits = true limit 4";
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		StringBuilder lyricTitles = new StringBuilder();
		String result = null;
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			// ptmt.setString(1, "year");
			resultSet = ptmt.executeQuery();
			if (resultSet.next()) {
				while (resultSet.next()) {
					lyricTitles.append(resultSet.getString("lyric_title") + ",");
				}
				result = new BaseDAO().notifications(lyricTitles, "Listen todays all time hits");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResultset(resultSet);
			closePtmt(ptmt);
			closeConnection();
		}
		return result;

	}

	public String sendPushNotificationForDecades() {

		ArrayList<Integer> lista = new ArrayList<Integer>();
		lista.add(196);
		lista.add(197);
		lista.add(198);
		lista.add(199);
		lista.add(200);
		lista.add(201);

		Collections.shuffle(lista);
		// System.out.println(lista.get(0));
		int randomNum = ThreadLocalRandom.current().nextInt(196, 198);
		String queryString = "SELECT movie_name FROM lyrics.l_movie where movie_release_date like ? order by rand() limit 4";
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		StringBuilder moviesTitles = new StringBuilder();
		String result = null;
		int decade = lista.get(0);
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setString(1, "%" + randomNum + "%");
			resultSet = ptmt.executeQuery();
			if (resultSet.next()) {
				while (resultSet.next()) {
					moviesTitles.append(resultSet.getString("movie_name") + ",");
				}
				result = new BaseDAO().notifications(moviesTitles, "Listen " + randomNum + "0's songs");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResultset(resultSet);
			closePtmt(ptmt);
			closeConnection();
		}
		return result;
	}
}