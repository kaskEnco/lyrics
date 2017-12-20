package com.lyrics.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lyrics.model.L_year;

public class LyricYearDAO extends BaseDAO{public List<L_year> findAll() {
	List<L_year> years = new ArrayList<L_year>();
	L_year year =null;
	String queryString = "SELECT * FROM L_year ";
	try {
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		resultSet = ptmt.executeQuery();
		while(resultSet.next()) {
			year = new L_year();
			year.setId(resultSet.getInt("id"));
			year.setLyircYear(resultSet.getInt("lyric_year"));
			years.add(year);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return  years;
}


public L_year findById(int Id) {
	L_year year = new L_year();
	String queryString = "SELECT * FROM L_year where id = ?  ";
	try {
		connection = getConnection();
		ptmt = connection.prepareStatement(queryString);
		ptmt.setInt(1, Id);
		resultSet = ptmt.executeQuery();
		while(resultSet.next()) {
			year.setId(resultSet.getInt("id"));
			year.setLyircYear(resultSet.getInt("lyric_year"));
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return  year;
}}
