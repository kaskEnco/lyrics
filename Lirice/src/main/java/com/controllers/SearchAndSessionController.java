package com.controllers;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.solr.common.SolrDocument;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lyrics.dao.BaseDAO;
import com.lyrics.dao.SendingMailDAO;
import com.lyrics.dao.SolrSearchDAO;
import com.lyrics.model.Device;
import com.lyrics.model.Mail;

@RestController
public class SearchAndSessionController {

	@PostMapping(value = "/mail", consumes = "application/json")
	public String MailDescription(@RequestBody Mail mail) {
		String mailSent = new SendingMailDAO().sendMial(mail);
		return mailSent;
	}

	@GetMapping(value = "/search/{Searchvalue}", produces = "application/json")
	public List<SolrDocument> findSearch(@PathVariable String Searchvalue) {
		List<SolrDocument> searchOp = new SolrSearchDAO().findSearch(Searchvalue);
		return searchOp;
	}
	
	@PostMapping(value = "/deviceId", produces = "application/json")
	@Transactional
	public void getAndroidDeviceId(@RequestBody Device deviceId) {
		{
			String id = deviceId.getId();
			new BaseDAO().addDeviceIdForAndroid(id);
		}
	}
}
