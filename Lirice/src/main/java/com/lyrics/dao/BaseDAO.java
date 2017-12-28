package com.lyrics.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class BaseDAO {

	Connection connection = null;
	

	public Connection getConnection() throws SQLException {
		if (connection == null) {
			// System.out.println("creating new connection");
			connection = LyricConnectionFactory.getInstance().getConnection();
		}
		return connection;
	}

	public BaseDAO() {

	}
	
	public Timestamp getTimestamp(Timestamp fromDb){
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
}
