package com.lyrics.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.jupiter.api.Test;
import com.lyrics.LatestLyrics;
import com.lyrics.model.Mail;

@SuppressWarnings("deprecation")
class LatestLyricsTest {

	@SuppressWarnings("resource")

	@Test
	void teluguLyrics() {
		HttpUriRequest request = new HttpGet("http://kasksolutions.com:8080/LiriceApp/teluguLyrics/1");
		HttpResponse response = null;
		StatusLine expected = null;
		try {
			response = new DefaultHttpClient().execute(request);
			expected = response.getStatusLine();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int actual = 200;
		assertEquals(expected.getStatusCode(), actual);
	}

	@SuppressWarnings("resource")
	@Test
	void teluguLyricsFalse() {
		HttpUriRequest request = new HttpGet("http://kasksolutions.com:8080/LiriceApp/teluguLyrics/null");// if -1 is
																											// used as
																											// id no
																											// error but
																											// returns
																											// null
		HttpResponse response = null;
		StatusLine expected = null;
		try {
			response = new DefaultHttpClient().execute(request);
			expected = response.getStatusLine();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int actual = 400;
		assertNotNull(request);
		assertEquals(expected.getStatusCode(), actual);
	}

	@SuppressWarnings("resource")
	@Test
	void yearByYear() {
		HttpUriRequest request = new HttpGet("http://kasksolutions.com:8080/LiriceApp/year/2017");
		HttpResponse response = null;
		StatusLine expected = null;
		try {
			response = new DefaultHttpClient().execute(request);
			expected = response.getStatusLine();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int actual = 200;
		assertEquals(expected.getStatusCode(), actual);
	}

	@SuppressWarnings("resource")
	@Test
	void yearByYearFalse() {
		HttpUriRequest request = new HttpGet("http://kasksolutions.com:8080/LiriceApp/year/null");
		HttpResponse response = null;
		StatusLine expected = null;
		try {
			response = new DefaultHttpClient().execute(request);
			expected = response.getStatusLine();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int actual = 400;
		assertEquals(expected.getStatusCode(), actual);
	}

	@SuppressWarnings("resource")
	@Test
	void yearByYearFalseNeg() {
		HttpUriRequest request = new HttpGet("http://kasksolutions.com:8080/LiriceApp/year/-1");
		HttpResponse response = null;
		StatusLine expected = null;
		try {
			response = new DefaultHttpClient().execute(request);
			expected = response.getStatusLine();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int actual = 500;
		assertEquals(expected.getStatusCode(), actual);
	}

	@SuppressWarnings("resource")
	@Test
	void years() {
		HttpUriRequest request = new HttpGet("http://kasksolutions.com:8080/LiriceApp/years");
		HttpResponse response = null;
		StatusLine expected = null;
		try {
			response = new DefaultHttpClient().execute(request);
			expected = response.getStatusLine();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int actual = 200;
		assertEquals(expected.getStatusCode(), actual);
	}

	@SuppressWarnings("resource")
	@Test
	void trending() {
		HttpUriRequest request = new HttpGet("http://kasksolutions.com:8080/LiriceApp/trending");
		HttpResponse response = null;
		StatusLine expected = null;
		try {
			response = new DefaultHttpClient().execute(request);
			expected = response.getStatusLine();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int actual = 200;
		assertEquals(expected.getStatusCode(), actual);
	}

	@SuppressWarnings("resource")
	@Test
	void trendingAll() {
		HttpUriRequest request = new HttpGet("http://kasksolutions.com:8080/LiriceApp/trendingAll");
		HttpResponse response = null;
		StatusLine expected = null;
		try {
			response = new DefaultHttpClient().execute(request);
			expected = response.getStatusLine();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int actual = 200;
		assertEquals(expected.getStatusCode(), actual);
	}

	@SuppressWarnings("resource")
	@Test
	void latestMovies() {
		HttpUriRequest request = new HttpGet("http://kasksolutions.com:8080/LiriceApp/latestMovies");
		HttpResponse response = null;
		StatusLine expected = null;
		try {
			response = new DefaultHttpClient().execute(request);
			expected = response.getStatusLine();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int actual = 200;
		assertEquals(expected.getStatusCode(), actual);
	}

	@SuppressWarnings("resource")
	@Test
	void latestMoviesAll() {
		HttpUriRequest request = new HttpGet("http://kasksolutions.com:8080/LiriceApp/latestMoviesAll");
		HttpResponse response = null;
		StatusLine expected = null;
		try {
			response = new DefaultHttpClient().execute(request);
			expected = response.getStatusLine();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int actual = 200;
		assertEquals(expected.getStatusCode(), actual);
	}

	@SuppressWarnings("resource")
	@Test
	void writer() {
		HttpUriRequest request = new HttpGet("http://kasksolutions.com:8080/LiriceApp/writer");
		HttpResponse response = null;
		StatusLine expected = null;
		try {
			response = new DefaultHttpClient().execute(request);
			expected = response.getStatusLine();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int actual = 200;
		assertEquals(expected.getStatusCode(), actual);
	}

	@SuppressWarnings("resource")
	@Test
	void writerByName() {
		HttpUriRequest request = new HttpGet("http://kasksolutions.com:8080/LiriceApp/writer/srimani");
		HttpResponse response = null;
		StatusLine expected = null;
		try {
			response = new DefaultHttpClient().execute(request);
			expected = response.getStatusLine();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int actual = 200;
		assertEquals(expected.getStatusCode(), actual);
	}

	@SuppressWarnings("resource")
	@Test
	void writerByMovie() {
		HttpUriRequest request = new HttpGet("http://kasksolutions.com:8080/LiriceApp/lyrics/9/Srimani");
		HttpResponse response = null;
		StatusLine expected = null;
		try {
			response = new DefaultHttpClient().execute(request);
			expected = response.getStatusLine();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int actual = 200;
		assertEquals(expected.getStatusCode(), actual);
	}

	@SuppressWarnings("resource")
	@Test
	void writerByMovieFalse() {
		HttpUriRequest request = new HttpGet("http://kasksolutions.com:8080/LiriceApp/lyrics/-1/abc");// Null pointer
																										// exception
		HttpResponse response = null;
		StatusLine expected = null;
		try {
			response = new DefaultHttpClient().execute(request);
			expected = response.getStatusLine();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int actual = 200;
		assertEquals(expected.getStatusCode(), actual);
	}

	@SuppressWarnings("resource")
	@Test
	void lyricById() {
		HttpUriRequest request = new HttpGet("http://kasksolutions.com:8080/LiriceApp/lyrics/9");
		HttpResponse response = null;
		StatusLine expected = null;
		try {
			response = new DefaultHttpClient().execute(request);
			expected = response.getStatusLine();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int actual = 200;
		assertEquals(expected.getStatusCode(), actual);
	}

	@SuppressWarnings("resource")
	@Test
	void movieById() {
		HttpUriRequest request = new HttpGet("http://kasksolutions.com:8080/LiriceApp/movies/9");
		HttpResponse response = null;
		StatusLine expected = null;
		try {
			response = new DefaultHttpClient().execute(request);
			expected = response.getStatusLine();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int actual = 200;
		assertEquals(expected.getStatusCode(), actual);
	}

	@SuppressWarnings("resource")
	@Test
	void movieByIdFalse() {
		HttpUriRequest request = new HttpGet("http://kasksolutions.com:8080/LiriceApp/movies/-1");// null pointer
																									// exception
		HttpResponse response = null;
		StatusLine expected = null;
		try {
			response = new DefaultHttpClient().execute(request);
			expected = response.getStatusLine();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int actual = 200;
		assertEquals(expected.getStatusCode(), actual);
	}

	/*
	 * @Test void mail() {
	 * 
	 * Mail mail = new Mail(); mail.setDescription("Integration testing");
	 * mail.setMailId("anvesh"); HttpUriRequest request = new
	 * HttpPost("http://kasksolutions.com:8080/LiriceApp/mail");
	 * request.setHeader("Accept", "application/json");
	 * request.setHeader("Content-type", "application/json");
	 * request.addHeader("mailDescription", "Integration testing");
	 * request.addHeader("mailId", "Anvesh"); //request. String json ="data={" +
	 * "\"username\": \"admin\", " + "\"first_name\": \"System\", " +
	 * "\"last_name\": \"Administrator\"" + "}"; try { StringEntity entity = new
	 * StringEntity(json); } catch (UnsupportedEncodingException e1) {
	 * 
	 * e1.printStackTrace(); } // request.setEntity(request);
	 * 
	 * HttpResponse response = null; StatusLine expected = null; try { response =
	 * new DefaultHttpClient().execute(request); expected =
	 * response.getStatusLine(); } catch (ClientProtocolException e) {
	 * e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
	 * HttpResponse response = Request.Post("http://www.example.com").bodyForm(
	 * Form.form().add("username", "John").add("password", "pass").build())
	 * .execute().returnResponse(); CloseableHttpClient client =
	 * HttpClients.createDefault(); HttpPost httpPost = new
	 * HttpPost("http://www.example.com");
	 * 
	 * ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
	 * params.add(new BasicNameValuePair("username", "John")); params.add(new
	 * BasicNameValuePair("password", "pass")); try {
	 * httpPost.setEntity((HttpEntity) new UrlEncodedFormEntity(params)); } catch
	 * (UnsupportedEncodingException e) {
	 * 
	 * e.printStackTrace(); } int actual = 200;
	 * assertEquals(expected.getStatusCode(), actual); CloseableHttpClient client =
	 * HttpClients.createDefault(); HttpPost httpPost = new
	 * HttpPost("http://kasksolutions.com:8080/LiriceApp/mail");
	 * 
	 * String json = "{\" +\r\n" +
	 * "                \"\\\"mailId\\\": \\\"integration testing\\\", \" +\r\n" +
	 * "                \"\\\"mailDescription\\\": \\\"Hi\\\", \" +\r\n" +
	 * "                \"}"; StringEntity entity = null; try { entity = new
	 * StringEntity(json); } catch (UnsupportedEncodingException e) {
	 * 
	 * e.printStackTrace(); } httpPost.setEntity(entity);
	 * httpPost.setHeader("Accept", "application/json");
	 * httpPost.setHeader("Content-type", "application/json");
	 * 
	 * HttpClient client = new DefaultHttpClient(); HttpPost post = new
	 * HttpPost("http://vogellac2dm.appspot.com/register"); HttpResponse response1 =
	 * null; try { ArrayList<NameValuePair> nameValuePairs = new
	 * ArrayList<NameValuePair>(); nameValuePairs.add(new
	 * BasicNameValuePair("mailId","123456789")); nameValuePairs.add(new
	 * BasicNameValuePair("mailDescription","123456789")); post.setEntity(new
	 * UrlEncodedFormEntity(nameValuePairs));
	 * 
	 * response1 = client.execute(post);
	 * 
	 * BufferedReader rd = new BufferedReader(new
	 * InputStreamReader(response.getEntity().getContent())); String line = "";
	 * while ((line = rd.readLine()) != null) { System.out.println(line); }
	 * 
	 * } catch (IOException e) { e.printStackTrace(); }
	 * 
	 * 
	 * int actual1 = response1.getStatusLine().getStatusCode(); int expected1 = 200;
	 * assertEquals(actual, expected);
	 * 
	 * }
	 */

	@Test
	void sendmail() throws IOException {

		LatestLyrics lyrics = new LatestLyrics();
		Mail mail = new Mail();
		mail.setDescription("integration testing");
		mail.setMailId("test");
		String mailresult = lyrics.MailDescription(mail);
		assertEquals(mailresult, "success");

		/*
		 * HttpClient httpclient; HttpPost httppost; HttpResponse response; int actual =
		 * 0; int expected = 200; ArrayList<NameValuePair> postParameters; httpclient =
		 * new DefaultHttpClient(); httppost = new
		 * HttpPost("http://kasksolutions.com:8080/LiriceApp/mail");
		 * 
		 * postParameters = new ArrayList<NameValuePair>(); postParameters.add(new
		 * BasicNameValuePair("mailId", "Hi")); postParameters.add(new
		 * BasicNameValuePair("mailDescription", "Integration testing"));
		 * 
		 * try { httppost.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));
		 * response = httpclient.execute(httppost); actual =
		 * response.getStatusLine().getStatusCode(); } catch
		 * (UnsupportedEncodingException e) { e.printStackTrace(); } catch
		 * (ClientProtocolException e) { e.printStackTrace(); } catch (IOException e) {
		 * e.printStackTrace(); }
		 * 
		 * 
		 * 
		 * JSONObject json = new JSONObject(); json.put("mailId",
		 * "Integration testing"); json.put("mailDescription", "with api");
		 * 
		 * HttpClient httpClient = new DefaultHttpClient(); HttpResponse response =
		 * null;
		 * 
		 * HttpPost request = new HttpPost("http://localhost:8080/LiriceApp/mail");
		 * StringEntity params = new StringEntity(json.toString());
		 * request.addHeader("content-type", "application/json");
		 * request.setEntity(params);
		 * 
		 * response = httpClient.execute(request); int actual =
		 * response.getStatusLine().getStatusCode(); int expected = 200;
		 * assertEquals(actual, expected);
		 * 
		 * 
		 * Map<String, Object> jsonValues = new HashMap<String, Object>();
		 * jsonValues.put("mailId", "integration testing");
		 * jsonValues.put("mailDescrioption", "with api");
		 * 
		 * JSONObject json = new JSONObject(jsonValues); String url =
		 * "http://localhost:8080/LiriceApp/mail"; DefaultHttpClient client = new
		 * DefaultHttpClient(); HttpPost post = new HttpPost(url);
		 * post.setHeader("Accept", "application/json"); //
		 * post.setHeader("headerValue", "HeaderInformation"); // setting json object to
		 * post request. StringEntity entity = new StringEntity(json.toString(),
		 * "UTF8"); entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
		 * "application/json")); post.setEntity(entity); // this is your response:
		 * HttpResponse response = client.execute(post); int expected =
		 * response.getStatusLine().getStatusCode(); int actual = 200;
		 * assertEquals(actual, expected); // System.out.println("Response: " +
		 * response.getStatusLine()); // return response.getStatusLine().toString();
		 */

	}

	@SuppressWarnings("resource")
	@Test
	void search() {
		HttpUriRequest request = new HttpGet("http://localhost:8080/LiriceApp/search/anan");
		HttpResponse response = null;
		StatusLine expected = null;
		try {
			response = new DefaultHttpClient().execute(request);
			response.removeHeaders(null);
			expected = response.getStatusLine();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int actual = 200;
		assertEquals(expected.getStatusCode(), actual);
	}

	@SuppressWarnings("resource")
	@Test
	void searchFalse() {
		HttpUriRequest request = new HttpGet("http://localhost:8080/LiriceApp/search/-1");
		HttpResponse response = null;
		StatusLine expected = null;
		try {
			response = new DefaultHttpClient().execute(request);
			response.removeHeaders(null);
			expected = response.getStatusLine();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int actual = 200;
		assertEquals(expected.getStatusCode(), actual);
	}
}
