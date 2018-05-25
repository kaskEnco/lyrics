package com.lyrics.dao.test;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.solr.common.SolrDocumentList;
import org.junit.jupiter.api.Test;

import com.lyrics.dao.SolrSearchDAO;

class SolrSearchDAOTest {

	SolrSearchDAO search;
		
	@Test
	void test() {
		search = new SolrSearchDAO();
		SolrDocumentList result = search.findSearch("Anan");
		assertNotNull(result);
	}
}
