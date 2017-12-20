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
	PreparedStatement ptmt = null;
	ResultSet resultSet = null;

	protected Connection getConnection() throws SQLException {
		Connection conn;
		conn = LyricConnectionFactory.getInstance().getConnection();
		return conn;
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
	
}
