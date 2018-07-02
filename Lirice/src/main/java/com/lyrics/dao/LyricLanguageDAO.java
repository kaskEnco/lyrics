package com.lyrics.dao;

import java.util.List;

import com.lyrics.model.L_language;

public class LyricLanguageDAO extends BaseDAO {

	public L_language findById(int langId) {
		List<L_language> lang = findAllLanguges();
		L_language language = new L_language() ;
		if(lang != null) {
			for (L_language list : lang) {
				if (list.getLangId() == langId) {
					 return list;
				}
			}
		}
					
		return language;
	}
}
