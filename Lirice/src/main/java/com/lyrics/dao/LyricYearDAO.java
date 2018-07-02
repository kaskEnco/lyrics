package com.lyrics.dao;

import java.util.List;

import com.lyrics.model.L_year;

public class LyricYearDAO extends BaseDAO {

	public L_year findById(int Id) {
		List<L_year> years = findAllYears();
		L_year yearObj = null;

			for (L_year list : years) {
				if (list.getYearId() == Id) {
					yearObj = list;
					break;
				}
			}

		return yearObj;
	
	}

	public L_year findByYear(int year) {
		 L_year yearsObj = null ;
		 List<L_year> years = findAllYears();
		 if (years != null) {
			 for (L_year list : years) {
			 if (list.getLyircYear() == year) {
			   yearsObj = list;
			   break;
			 }
		 }
		 
		 }
		return yearsObj;
	}

}
