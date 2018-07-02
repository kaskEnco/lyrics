package com.lyrics.dao.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.lyrics.dao.LyricMovieDAO;
import com.lyrics.dao.SolrSearchDAO;
import com.lyrics.model.L_lyrics;

@RunWith(MockitoJUnitRunner.class)
class SolrSearchDAOTest {

	@Mock
	SolrSearchDAO search;
	
	SolrSearchDAO mockSearch = mock(SolrSearchDAO.class);
	
	@Test
	public void solrTest() {
		search = new SolrSearchDAO();
		SolrDocumentList result = search.findSearch("Anan");
		assertNotNull(result);
	}

	@Test
	public void solrMock() {

		SolrDocument doc = new SolrDocument();
		doc.addField("lyric_Title", "Dhooram");
		SolrDocumentList listDoc = new SolrDocumentList();
		listDoc.add(doc);
		when(mockSearch.findSearch(anyString())).thenReturn(listDoc);
		SolrDocumentList docs = mockSearch.findSearch("anan");
		assertEquals("Dhooram", docs.get(0).get("lyric_Title"));

	}
}
