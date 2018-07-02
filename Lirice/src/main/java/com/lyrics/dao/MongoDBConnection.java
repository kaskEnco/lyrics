package com.lyrics.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {

	private static MongoDBConnection mongoConnection;

	public static MongoDBConnection getInstance() {
		if (mongoConnection == null) {
			synchronized (MongoDBConnection.class) {
				if (mongoConnection == null) {
					mongoConnection = new MongoDBConnection();
				}
			}
		}
		return mongoConnection;
	}

	public DB getConnection()  {
		MongoClient mongoClient = null;
		DB dbConnection = null;
		// MongoDatabase database = null ;
		try {
			mongoClient = new MongoClient("localhost", 27017);
			dbConnection = mongoClient.getDB("lyrics");
		//	database = mongoClient.getDatabase("lyrics");
		} catch (Exception e) {
			System.out.println(e);
		}
		return dbConnection;
	}
}
