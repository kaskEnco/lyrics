package com.lyrics.model;

import java.io.Serializable;

public class Mail implements Serializable{
	private String mailId;
	private String mailDescription;

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getMailDescription() {
		// TODO Auto-generated method stub
		return mailDescription;
	}

	public void setDescription(String mailDescription) {
		this.mailDescription = mailDescription;
	}

}