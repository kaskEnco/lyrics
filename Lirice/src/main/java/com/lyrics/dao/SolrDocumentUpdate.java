package com.lyrics.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.LBHttpSolrClient;
import org.apache.solr.common.SolrInputDocument;

import com.lyrics.model.L_year;



public class SolrDocumentUpdate extends BaseDAO{
	
	SolrClient solrClient;
	

	Connection connection;
	PreparedStatement ptmt = null;
	ResultSet resultSet = null;
	int lyric_id;
	String lyric_title;
	String movie_name;
	String movie_id;
	String writer_name;
	Timestamp release_date;
	public CloudSolrClient getSolrConnection() throws SolrServerException, IOException{
		String zkHostString = "localhost:2181";
		solrClient = new CloudSolrClient.Builder().withZkHost(zkHostString).build();
		((CloudSolrClient) solrClient).setDefaultCollection("mdslyrics");
		
		 
	//url.add("http://localhost:8984/solr/#/movies/");
	//solrClient = new CloudSolrClient.Builder().withSolrUrl(url).build();
				
	return (CloudSolrClient) solrClient;
	}
	
	public void insertDocument() throws SolrServerException, IOException {
	
		solrClient= getSolrConnection();
		
			String queryToSelectDocument = "SELECT * FROM l_lyrics ";
			try {
				connection = DriverManager.getConnection(  
						"jdbc:mysql://localhost:3306/lyrics","root","root");  
				ptmt = connection.prepareStatement(queryToSelectDocument);
				resultSet = ptmt.executeQuery();
				while(resultSet.next()) {
					lyric_id = resultSet.getInt("id");
					movie_id = resultSet.getString("movie_id");
					
					lyric_title = resultSet.getString("lyric_title");
					writer_name = resultSet.getString("writer_name");
					/*String query = "select movie_name,movie_release_date from l_movie where id = ?";
					ptmt = connection.prepareStatement(query);
					ptmt.setInt(1, Integer.parseInt(movie_id));
					resultSet = ptmt.executeQuery();
					//release_date = resultSet.getTimestamp("movie_release_date");
					movie_name = resultSet.getString("movie_name");*/
					
					 SolrInputDocument doc = new SolrInputDocument();
				     // doc.addField("id", lyric_id);
				      doc.addField("movie_id", movie_id);
				     // doc.addField("movie_name", movie_name);
				      doc.addField("lyric_title", lyric_title);
				     // doc.addField("release_date", release_date);
				      doc.addField("writer_name", writer_name);
				      solrClient.add(doc , 10000);
				      solrClient.commit();
				      //solrClient.close();
				}
				
				
		} catch (SQLException | SolrServerException | IOException e1) {
			// TODO Auto-generated catch block
			try {
				resultSet.close();
				ptmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			e1.printStackTrace();
		}
	 }
	public static void main(String[] srgs) throws ClassNotFoundException, SolrServerException, IOException {
		Class.forName("com.mysql.jdbc.Driver");  
	new SolrDocumentUpdate().insertDocument();	
	}
}
