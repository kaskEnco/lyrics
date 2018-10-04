package com.lyrics.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lyrics.Constants;
import com.lyrics.model.L_allTimeHits;
import com.lyrics.model.L_language;
import com.lyrics.model.L_lyrics;
import com.lyrics.model.L_movie;
import com.lyrics.model.L_year;
import com.lyrics.model.NotificationData;
import com.lyrics.model.NotificationRequestModel;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import net.spy.memcached.MemcachedClient;

public class BaseDAO {

	Connection connection = null;
	MemcachedClient cache = null;
	DB mongoClientDB = null;

	public Connection getConnection() throws SQLException {
		if (connection == null) {
			// System.out.println("creating new connection");
			connection = LyricConnectionFactory.getInstance().getConnection();
		}
		return connection;
	}

	public MemcachedClient getMemcacheConnection() {
		try {
			if (cache == null) {
				cache = MemcachedConncetionFactory.getInstance().getConnection();
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return cache;
	}

	public DB getMongoConnection() {
		if (mongoClientDB == null) {
			mongoClientDB = MongoDBConnection.getInstance().getConnection();
		}
		return mongoClientDB;
	}

	public BaseDAO() {

	}

	public Timestamp getTimestamp(Timestamp fromDb) {
		Date date = new Date(Long.valueOf(fromDb.toString()));
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatted = format.format(date);
		Timestamp timeStamp = Timestamp.valueOf(formatted);
		return timeStamp;
	}

	public void closePtmt(PreparedStatement ptmt) {

		try {
			if (ptmt != null)
				ptmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void closeConnection() {

		// System.out.println("local connection : " + connection1);
		// System.out.println("global connection : " + connection);
		try {

			if (connection != null)
				connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			connection = null;
		}
	}

	public static void closeResultset(ResultSet resultSet) {

		try {
			if (resultSet != null)
				resultSet.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<L_year> findAllYears() {
		List<L_year> years = null;
		if (Constants.USE_MEMCACHED) {
			cache = getMemcacheConnection();
			years = (List<L_year>) cache.get(Constants.YEARS_KEY);
		}

		if (years != null)
			return years;

		years = new ArrayList<L_year>();
		L_year year = null;
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		String queryString = "SELECT * FROM l_year ";
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			resultSet = ptmt.executeQuery();
			while (resultSet.next()) {
				year = new L_year();
				year.setYearId(resultSet.getInt("id"));
				year.setLyircYear(resultSet.getInt("lyric_year"));
				years.add(year);

			}
			cache.add(Constants.YEARS_KEY, 0, years);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeResultset(resultSet);
			closePtmt(ptmt);
			closeConnection();
		}

		return years;

	}

	public List<L_language> findAllLanguges() {
		List<L_language> languages = null;
		L_language lang;
		if (Constants.USE_MEMCACHED) {
			cache = getMemcacheConnection();
			languages = (List<L_language>) cache.get(Constants.LANG_LIST);
		}

		if (languages != null)
			return languages;

		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		String queryString = "SELECT * FROM l_language ";
		try {

			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			resultSet = ptmt.executeQuery();
			languages = new ArrayList<L_language>();
			while (resultSet.next()) {
				lang = new L_language();
				lang.setLangId(resultSet.getInt("id"));
				lang.setLanguage(resultSet.getString("lang_name"));
				languages.add(lang);
			}
			cache.add(Constants.LANG_LIST, 0, languages);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeResultset(resultSet);
			closePtmt(ptmt);
			closeConnection();
		}

		return languages;
	}

	public List<L_lyrics> findAllLyrics() {
		List<L_lyrics> lyrics = null;
		if (Constants.USE_MEMCACHED) {
			cache = getMemcacheConnection();
			lyrics = (List<L_lyrics>) cache.get(Constants.LYRIC_CONTENT);
		}

		if (lyrics != null)
			return lyrics;

		lyrics = new ArrayList<L_lyrics>();
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
				lyric.setUrl(resultSet.getString("url"));
				lyrics.add(lyric);
			}
			cache.add(Constants.LYRIC_CONTENT, 0, lyrics);

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

	public List<L_movie> findAllMovies() {
		List<L_movie> movies = null;
		if (Constants.USE_MEMCACHED) {
			cache = getMemcacheConnection();
			movies = (List<L_movie>) cache.get(Constants.MOVIES_LIST);
		}

		if (movies != null)
			return movies;

		movies = new ArrayList<L_movie>();
		LyricLanguageDAO langDAO = new LyricLanguageDAO();
		LyricYearDAO yearDAO = new LyricYearDAO();
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		String queryString = "SELECT * FROM l_movie ";
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			resultSet = ptmt.executeQuery();
			while (resultSet.next()) {
				L_movie movie = new L_movie();
				movie.setMovieId(resultSet.getInt("id"));
				movie.setLanguage(langDAO.findById(resultSet.getInt("lang_id")));
				movie.setYear(yearDAO.findById(resultSet.getInt("movie_year_id")));
				movie.setMovieName(resultSet.getString("movie_name"));
				movie.setMovieReleaseDate(resultSet.getTimestamp("movie_release_date"));
				movie.setCreationDate(resultSet.getTimestamp("creation_time"));
				movie.setUpdationDate(resultSet.getTimestamp("updation_time"));
				movies.add(movie);
			}
			cache.add(Constants.MOVIES_LIST, 0, movies);
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

	public List<L_lyrics> findAllTeluguLyrics() {

		List<L_lyrics> teluguLyrics = null;
		L_lyrics content;

		if (Constants.USE_MEMCACHED) {
			cache = getMemcacheConnection();
			teluguLyrics = (List<L_lyrics>) cache.get(Constants.TELUGU_LYRIC_CONTENT);
		}

		if (teluguLyrics != null)
			return teluguLyrics;

		teluguLyrics = new ArrayList<L_lyrics>();
		// MongoClient mongoClient = new MongoClient("localhost", 27017);
		// MongoClient mongoClient = getMongoConnection();
		DB db = getMongoConnection();
		// DB db = mongoClient.getDB("lyrics");
		DBCollection collection = db.getCollection("teluguLyrics");
		DBCursor cursor = collection.find();

		int n = collection.find().count();
		Object value = null;
		for (int i = 0; i < n; i++) {
			value = cursor.next();
			JSONObject obj = new JSONObject((Map) value);
			content = new L_lyrics();
			// content.set_id((int) obj.get("_id"));
			content.setLyricContent((String) obj.get("lyricContent"));
			content.setUrl((String) obj.get("url"));

			teluguLyrics.add(content);

		}
		cache.add(Constants.TELUGU_LYRIC_CONTENT, 0, teluguLyrics);

		return teluguLyrics;
	}

	public List<L_allTimeHits> findAllTimeHits() {
		List<L_allTimeHits> allTimeHits = null;
		L_allTimeHits content;

		if (Constants.USE_MEMCACHED) {
			cache = getMemcacheConnection();
			allTimeHits = (List<L_allTimeHits>) cache.get(Constants.ALL_TIME_HITS);
		}

		if (allTimeHits != null)
			return allTimeHits;

		allTimeHits = new ArrayList<L_allTimeHits>();

		// to select songs randomly use below query
		// (select * from l_lyrics where all_time_hits = false order by rand() limit
		// 5)order by updation_time desc;
		File file = new File("/tmp/count.txt"); // ("/tmp/count.txt") in aws
		BufferedReader br;
		String year = null;
		try {
			br = new BufferedReader(new FileReader(file));
			year = br.readLine();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// int year = Integer.parseInt(yearInString);
		LyricMovieDAO movieDAO = new LyricMovieDAO();
		String queryString = "SELECT * FROM lyrics.l_lyrics where l_lyrics.all_time_hits=true and (select true from l_movie where l_lyrics.movie_id=id and movie_release_date < ?) order by rand() limit 100;";
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setString(1, year);
			resultSet = ptmt.executeQuery();
			while (resultSet.next()) {
				content = new L_allTimeHits();
				content.setLyricId(resultSet.getInt("id"));
				content.setLyricTitle(resultSet.getString("lyric_title"));
				content.setWriterName(resultSet.getString("writer_name"));
				L_movie movie = movieDAO.findById(resultSet.getInt("movie_id"));
				content.setMovieId(movie.getMovieId());
				content.setMovieName(movie.getMovieName());
				content.setReleaseDate(movie.getMovieReleaseDate());
				content.setMovie_id(resultSet.getString("movie_id"));
				content.setWriterName(resultSet.getString("writer_name"));
				content.setUrl(resultSet.getString("url"));
				allTimeHits.add(content);
			}
			cache.add(Constants.ALL_TIME_HITS, 0, allTimeHits);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeResultset(resultSet);
			closePtmt(ptmt);
			closeConnection();
		}

		return allTimeHits;

	}

	public void addDeviceIdForAndroid(String deviceId, String fcmToken) {
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		String queryCheck = "select * from l_session where device_name = ?";
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(queryCheck);
			ptmt.setString(1, deviceId);
			resultSet = ptmt.executeQuery();
			if (resultSet.next() == false) {
				String query = " insert into lyrics.l_session values (null,?,null,?) ";
				connection = getConnection();
				ptmt = connection.prepareStatement(query);
				ptmt.setString(1, deviceId);
				ptmt.setString(2, fcmToken);
				ptmt.executeUpdate();
				ptmt.close();
			} else {
				String query = " update lyrics.l_session set fcmtoken =? where device_name = ? ";
				connection = getConnection();
				ptmt = connection.prepareStatement(query);
				ptmt.setString(1, fcmToken);
				ptmt.setString(2, deviceId);
				ptmt.executeUpdate();
				ptmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				connection.close();
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public List<L_lyrics> findAllLyricsByWriter(String writerName) {
		List<L_lyrics> lyrics = null;
		if (Constants.USE_MEMCACHED) {
			cache = getMemcacheConnection();
			lyrics = (List<L_lyrics>) cache.get("writerlyrics" + writerName);
		}

		if (lyrics != null)
			return lyrics;

		lyrics = new ArrayList<L_lyrics>();
		LyricMovieDAO movieDAO = new LyricMovieDAO();
		L_lyrics lyric = null;
		String queryString = "SELECT * FROM l_lyrics where writer_name=?";
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setString(1, writerName);
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
				lyric.setUrl(resultSet.getString("url"));
				lyrics.add(lyric);
			}
			cache.add("writerlyrics" + writerName, 0, lyrics);

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
	
	public String notifications(StringBuilder lyricTitles,String lyricBody) {

		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		String query = "SELECT fcmtoken FROM lyrics.l_session ";
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(query);
			//ptmt.setString(1, year);
			resultSet = ptmt.executeQuery();
			while (resultSet.next()) {
				String fcmtoken = resultSet.getString("fcmtoken");
				HttpClient httpClient = HttpClientBuilder.create().build();
				HttpPost postRequest = new HttpPost("https://fcm.googleapis.com/fcm/send");

				// we already created this model class.
				// we will convert this model class to json object using google gson library.

				NotificationRequestModel notificationRequestModel = new NotificationRequestModel();
				NotificationData notificationData = new NotificationData();

				notificationData.setBody(lyricTitles+"..");
				notificationData.setTitle(lyricBody);
				notificationData.setPriority("high");
				notificationData.setSound("default");
				notificationData.setShow_in_foreground("true");
				notificationData.setShow_in_background("true");
				notificationData.setFcmMessageType("notification");
				notificationData.setClick_action("example.com.pushnotifications_TARGET_NOTIFICATION");
				notificationData.setIcon("ic_launcher");
				notificationRequestModel.setNotification(notificationData);
				notificationRequestModel.setTo(fcmtoken);

				Gson gson = new Gson();
				Type type = new TypeToken<NotificationRequestModel>() {
				}.getType();

				String json = gson.toJson(notificationRequestModel, type);

				StringEntity input = new StringEntity(json);
				input.setContentType("application/json");

				// server key of your firebase project goes here in header field.
				// You can get it from firebase console.

				postRequest.addHeader("Authorization",
						"key=AAAA-IP9V4Y:APA91bHkPMutN_7VuiXQEELdzzOKdK5y-Lr9oCwygCwaOfW8pzOvvFXm0No3fLJNZUqZIiQ1d9Gj3ihYmOp-sfqnlDRM0dEgwLy8pPpnyv4UqUQ58W3PUEeSEOcUBGqcwAcAKKLP8QwT");
				postRequest.setEntity(input);

				System.out.println("request:" + json);
				httpClient.execute(postRequest);
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	
	}

}