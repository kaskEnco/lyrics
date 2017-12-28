package com.lyrics.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lyrics.model.L_language;
import com.lyrics.model.L_movie;
import com.lyrics.model.L_year;

public class LyricLanguageDAO extends BaseDAO{
	
	public L_language findALl() {
		L_language lang = new L_language();
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		String queryString = "SELECT * FROM l_language ";
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			resultSet = ptmt.executeQuery();
			while(resultSet.next()) {
				lang.setLangId(resultSet.getInt("id"));
				lang.setLanguage(resultSet.getString("lang_name"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return  lang;
	}
	
	public L_language findById(int langId) {
		L_language lang = new L_language();
		PreparedStatement ptmt = null;
		ResultSet resultSet = null;
		String queryString = "SELECT * FROM l_language where id = ?  ";
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setInt(1, langId);
			resultSet = ptmt.executeQuery();
			while(resultSet.next()) {
				lang.setLangId(resultSet.getInt("id"));
				lang.setLanguage(resultSet.getString("lang_name"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeResultset(resultSet);
			closePtmt(ptmt);
			closeConnection();
		}
		
		return  lang;
	}
}
