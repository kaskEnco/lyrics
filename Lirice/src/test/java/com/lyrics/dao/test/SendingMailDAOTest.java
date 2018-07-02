package com.lyrics.dao.test;


import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.lyrics.dao.LyricMovieDAO;
import com.lyrics.dao.SendingMailDAO;
import com.lyrics.model.Mail;


@RunWith(MockitoJUnitRunner.class)
class SendingMailDAOTest {

	@Mock
	SendingMailDAO sentmail;
	
	SendingMailDAO mockMail = mock(SendingMailDAO.class);

/*	@Test
	public void mail() {
			
		mail = new Mail();
		mail.setMailId("anvesh");
		mail.setDescription("unit test mail");
		sentmail = new SendingMailDAO();
		sentmail.sendMial(mail);
		assertNotNull(sentmail);
	}*/
	
	@Test
	public void mailMock() {
		
		String mailResult = "SUCCESS";
		Mail mail = new Mail();
		when(mockMail.sendMial(anyObject())).thenReturn(mailResult);
		String mailExpected = mockMail.sendMial(mail);
		assertEquals(mailResult, mailExpected);
			
	}
}
