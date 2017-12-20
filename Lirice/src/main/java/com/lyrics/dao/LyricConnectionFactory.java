package com.lyrics.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LyricConnectionFactory {
	String driverClassName = "com.mysql.jdbc.Driver";
	String connectionUrl = "jdbc:mysql://localhost:3306/lyrics";
	String dbUser = "root";
	String dbPwd = "root123";
	
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
