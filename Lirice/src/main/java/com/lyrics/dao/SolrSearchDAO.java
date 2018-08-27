package com.lyrics.dao;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.LBHttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;



public class SolrSearchDAO {

	
	SolrClient solrClient;
	SolrClient lbHttpSolrClient ;
	 
	public SolrClient getSolrClient() {
		//solrClient = new HttpSolrClient.Builder().withBaseSolrUrl("").withBaseSolrUrl("").build();
		//lbHttpSolrClient = new LBHttpSolrClient("http://localhost:8983/solr/lyrics", "http://localhost:7574/solr/lyricsmore");
		//lbHttpSolrClient = new LBHttpSolrClient.Builder().withBaseSolrUrls("http://localhost:8983/solr/lyrics","http://localhost:7574/solr/lyricscore","http://localhost:8984/solr/lyricsmovies").build();
		lbHttpSolrClient = new LBHttpSolrClient.Builder().withBaseSolrUrls("http://localhost:8983/solr/lyrics").build();
		return lbHttpSolrClient;
	}

	public SolrDocumentList findSearch(String Searchvalue) {

		
		lbHttpSolrClient = getSolrClient();

		SolrQuery query = new SolrQuery();
		query.setQuery("*"+Searchvalue+"*");
		// query.addFilterQuery("writer_name:*an*","lyric_title:*dhoo*");
		query.setFields("id", "writer_name", "movie_name", "lyric_title","movie_id", "movie_release_date", "writer_name");
		query.setStart(0);
		 query.setRows(500); //to specify max number of rows to be retrieved
		// query.setTermsMaxCount(25);
//		query.set("defType", "json");
		//query.set("wt","json");

		QueryResponse response = null;
		try {
			response = lbHttpSolrClient.query(query);
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


