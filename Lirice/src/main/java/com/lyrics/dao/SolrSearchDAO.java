package com.lyrics.dao;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;



public class SolrSearchDAO {

	@Autowired
	SolrClient solrClient;

	@Bean
	public SolrClient getSolrClient() {
		String solrUrl = "http://localhost:8983/solr/movie";
		return new HttpSolrClient.Builder(solrUrl).build();
	}

	public SolrDocumentList findSearch(String Searchvalue) {

		
		solrClient = getSolrClient();

		SolrQuery query = new SolrQuery();
		query.setQuery("*"+Searchvalue+"*");
		// query.addFilterQuery("writer_name:*an*","lyric_title:*dhoo*");
		query.setFields("id", "writer_name", "movie_name", "lyric_title","movie_id", "movie_release_date");
		query.setStart(0);
		 query.setRows(500); //to specify max number of rows to be retrieved
		// query.setTermsMaxCount(25);
//		query.set("defType", "json");
		//query.set("wt","json");

		QueryResponse response = null;
		try {
			response = solrClient.query(query);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SolrDocumentList documents = response.getResults();

		return documents;
}}


