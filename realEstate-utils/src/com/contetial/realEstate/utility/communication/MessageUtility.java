package com.contetial.realEstate.utility.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.contetial.realEstate.utility.propReader.ReadConfigurations;

public class MessageUtility {	

	private static String retval="";
	private static String SMSSender(
			String user,String pwd,String to,String msg,String sid,String fl, String smsMethod){
		String rsp="";
		try {
			// Construct The Post Data
			String data = prepareMessageString(user, pwd, to, msg, sid,fl,smsMethod);
			//Push the HTTP Request
			
			String smsURL = ReadConfigurations.getInstance("./conf/application.properties").
					getProps().getProperty("smsURL");
			if(smsURL.isEmpty()){
				smsURL = "http://cloud.smsindiahub.in/vendorsms/pushsms.aspx";
			}
			URL url = new URL(smsURL);
			
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
			wr.write(data);
			wr.flush();
			//Read The Response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				// Process line...
				retval += line;
			}
			wr.close();
			rd.close();			
			System.out.println(retval);
			rsp = retval; 
		}catch (Exception e) {
			e.printStackTrace();
		}
		return rsp;
	}
	
	private static String prepareMessageString(String user, String pwd, String to, 
			String msg, String sid, String fl, String smsMethod) throws UnsupportedEncodingException {
		StringBuffer data = new StringBuffer();
		data.append(URLEncoder.encode("user", "UTF-8")).append("=").append(URLEncoder.encode(user, "UTF-8"))
		.append("&")
		.append(URLEncoder.encode("password", "UTF-8")).append("=").append(URLEncoder.encode(pwd, "UTF-8"))
		.append("&")
		.append(URLEncoder.encode("msisdn", "UTF-8")).append("=").append(URLEncoder.encode(to, "UTF-8"))
		.append("&")
		.append(URLEncoder.encode("msg", "UTF-8")).append("=").append(URLEncoder.encode(msg, "UTF-8"))
		.append("&")
		.append(URLEncoder.encode("sid", "UTF-8")).append("=").append(URLEncoder.encode(sid, "UTF-8"))
		.append("&")
		.append(URLEncoder.encode("fl", "UTF-8")).append("=").append(URLEncoder.encode(fl, "UTF-8"));
		if("production".equals(smsMethod)){
			data.append("&")
			.append(URLEncoder.encode("gwid", "UTF-8")).append("=").append(URLEncoder.encode("2", "UTF-8"));
		}
		return data.toString();
	}
	
	public static String sendSMS(String to, String message){
		Boolean smsSend = Boolean.FALSE;
		try {
			smsSend = Boolean.valueOf(ReadConfigurations.getCurrentInstance("./conf/application.properties").
					getProps().getProperty("smsSend"));
			if(!smsSend){
				smsSend = Boolean.FALSE;
			}
		} catch (IOException e) {
			e.printStackTrace();
			smsSend = Boolean.FALSE;
		}
		
		String returnMessage = "Inactive Service";
		
		if(smsSend){
			returnMessage = prepareAndSendSMS(to, message);
		}
		return returnMessage;
	}

	private static String prepareAndSendSMS(String to, String message) {
		String returnMessage;
		String smsUser="";
		try {
			smsUser = ReadConfigurations.getInstance("./conf/application.properties").
					getProps().getProperty("smsUser");
			if(smsUser.isEmpty()){
				smsUser = "contetialTest";
			}
		} catch (IOException e) {
			e.printStackTrace();
			smsUser = "contetialTest";
		}

		String smsPwd="";
		try {
			smsPwd = ReadConfigurations.getInstance("./conf/application.properties").
					getProps().getProperty("smsPwd");
			if(smsPwd.isEmpty()){
				smsPwd = "Im1Mould";
			}
		} catch (IOException e) {
			e.printStackTrace();
			smsPwd = "Im1Mould";
		}

		String smsSID="";
		try {
			smsSID = ReadConfigurations.getInstance("./conf/application.properties").
					getProps().getProperty("smsSID");
			if(smsSID.isEmpty()){
				smsSID = "WEBSMS";
			}
		} catch (IOException e) {
			e.printStackTrace();
			smsSID = "WEBSMS";			
		}

		String smsFlag="";
		try {
			smsFlag = ReadConfigurations.getInstance("./conf/application.properties").
					getProps().getProperty("smsFlag");
			if(smsFlag.isEmpty()){
				smsFlag = "0";
			}
		} catch (IOException e) {
			e.printStackTrace();
			smsFlag = "0";
		}

		String smsMethod="";
		try {
			smsMethod = ReadConfigurations.getInstance("./conf/application.properties").
					getProps().getProperty("smsMethod");
			if(smsMethod.isEmpty()){
				smsMethod = "test";
			}
		} catch (IOException e) {
			e.printStackTrace();
			smsMethod = "test";
		}

		to = "91"+to;
		returnMessage = SMSSender(smsUser,smsPwd,to,message,smsSID,smsFlag,smsMethod);
		return returnMessage;
	}
	
	public static void main(String[] args) {
		//String response = SMSSender("contetialTest", "Im1Mould", "919730756440", "test message", "WEBSMS", "0");
		if(null==args[0]){
			args[0] = "9970302955";
		}
		if(null==args[1]){
			args[0] = "This is a test message";
		}
		String response = sendSMS(args[0],args[1]);
		System.out.println(response);
	}
	
}

