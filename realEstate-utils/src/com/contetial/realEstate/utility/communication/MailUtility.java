package com.contetial.realEstate.utility.communication;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.contetial.realEstate.utility.propReader.ReadConfigurations;

public class MailUtility{

	private String from;
	private String to;
	private String subject;
	private String text;

	public MailUtility(String from, String to, String subject, String text){
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.text = text;
	}

	public void send() throws MessagingException{
		
		Boolean emailSend = Boolean.FALSE;
		try {
			emailSend = Boolean.valueOf(ReadConfigurations.getCurrentInstance("./conf/application.properties").
					getProps().getProperty("emailSend"));
			if(!emailSend){
				emailSend = Boolean.FALSE;
			}
		} catch (IOException e) {
			e.printStackTrace();
			emailSend = Boolean.FALSE;
		}
		
		if(emailSend){
			Session mailSession = getMailSession();

			Message simpleMessage = new MimeMessage(mailSession);

			try {		
				createSimpleMessage(simpleMessage);		
				Transport.send(simpleMessage);
			} catch (MessagingException e) {
				e.printStackTrace();
				throw e;
			}
		}else{
			System.out.println("############## Email Service is inactive ###########");
		}
	}

	private void createSimpleMessage(Message simpleMessage) throws MessagingException {
		InternetAddress fromAddress = null;
		InternetAddress toAddress = null;
		try {
			fromAddress = new InternetAddress(from);
			toAddress = new InternetAddress(to);
		} catch (AddressException e) {
			throw e;
		}

		try {
			String messageType = "text/html";
			simpleMessage.setFrom(fromAddress);
			simpleMessage.setRecipient(RecipientType.TO, toAddress);
			simpleMessage.setSubject(subject);
			simpleMessage.setContent(text,messageType);
		} catch (MessagingException e) {
			throw e;
		}
	}

	private Session getMailSession() {
		String smtpHost = "smtp.gmail.com";
		try {
			smtpHost = ReadConfigurations.getInstance("./conf/application.properties").
					getProps().getProperty("smtpHost");
			if(smtpHost.isEmpty()){
				smtpHost = "smtp.gmail.com";
			}
		} catch (IOException e) {
			e.printStackTrace();
			smtpHost = "smtp.gmail.com";
		}
		
		String smtpPort = "465";
		try {
			smtpPort = ReadConfigurations.getInstance("./conf/application.properties").
					getProps().getProperty("smtpPort");
			if(smtpPort.isEmpty()){
				smtpPort = "465";
			}
		} catch (IOException e) {
			e.printStackTrace();
			smtpPort = "465";
		}
		
		String smtpUser = "user";
		try {
			smtpUser = ReadConfigurations.getInstance("./conf/application.properties").
					getProps().getProperty("smtpUser");
			if(smtpUser.isEmpty()){
				smtpUser = "user";
			}
		} catch (IOException e) {
			e.printStackTrace();
			smtpUser = "user";
		}
		
		String smtpPassword = "password";
		try {
			smtpPassword = ReadConfigurations.getInstance("./conf/application.properties").
					getProps().getProperty("smtpPassword");
			if(smtpPassword.isEmpty()){
				smtpPassword = "password";
			}
		} catch (IOException e) {
			e.printStackTrace();
			smtpPassword = "password";
		}
		
		final String SMTP_USERNAME = smtpUser;
        final String SMTP_PASSWORD = smtpPassword;
		
		Properties props = new Properties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", smtpPort);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.user", smtpUser);
		props.put("mail.smtp.password", smtpPassword);
		
		Session mailSession = Session.getInstance(props, new Authenticator() {			
            protected PasswordAuthentication getPasswordAuthentication() {            	
                return new PasswordAuthentication(
                		SMTP_USERNAME, SMTP_PASSWORD);
            }
        });
		return mailSession;
	}
}