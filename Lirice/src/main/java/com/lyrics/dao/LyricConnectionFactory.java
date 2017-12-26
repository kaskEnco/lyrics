package com.lyrics.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LyricConnectionFactory {
	String driverClassName = "com.mysql.jdbc.Driver";
	String connectionUrl = "jdbc:mysql://kask-db-instance.c1rialg0ee8x.us-east-1.rds.amazonaws.com:3306/lyrics";
	String dbUser = "kaskuser";
	String dbPwd = "kask4all";
	
	private static LyricConnectionFactory connectionFactory = null;
	
	private LyricConnectionFactory() {
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		conn = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
		return conn;
	}
	
	public static LyricConnectionFactory getInstance() {
		if (connectionFactory == null) {
			connectionFactory = new LyricConnectionFactory();
		}
		return connectionFactory;
	}

}
