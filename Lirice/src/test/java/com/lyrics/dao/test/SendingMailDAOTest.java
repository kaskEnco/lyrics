package com.lyrics.dao.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.lyrics.dao.SendingMailDAO;
import com.lyrics.model.Mail;

class SendingMailDAOTest {

	SendingMailDAO sentmail;

	Mail mail;

	@Test
	void test() {
		mail = new Mail();
		mail.setMailId("anvesh");
		mail.setDescription("unit test mail");
		sentmail = new SendingMailDAO();
		sentmail.sendMial(mail);
		assertNotNull(sentmail);
	}
}
