package com.contetial.realEstate.bot.jobs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.log4j.Logger;
import org.quartz.InterruptableJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.UnableToInterruptJobException;

import com.contetial.realEstate.utility.propReader.ReadConfigurations;

public class ReminderJob implements Job, InterruptableJob{

	private static Logger log = Logger.getLogger(ReminderJob.class.getName());

	public ReminderJob() {}

	AtomicReference<Thread> runningThread = new AtomicReference<Thread>();
	AtomicBoolean stopFlag = new AtomicBoolean(false);    
	private JobKey jobKey= null;

	@Override
	public void execute(JobExecutionContext context)throws JobExecutionException{

		this.runningThread.set(Thread.currentThread());
		setJobKey(context.getJobDetail().getKey());

		try {
			if (!stopFlag.get()){

				log.info("execute - ReminderJob Thread Start ");
				
				String urlAddress =
						ReadConfigurations.getInstance("./conf/realEstateBot.properties")
						.getProps().getProperty("urlAddress");

				URL url = new URL(urlAddress+"/rest/commService/remindCustomers");
				log.info("Target URL = "+url.getPath());
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");

				if (conn.getResponseCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ conn.getResponseCode());
				}

				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));

				String output;
				log.debug("Output from Server ...");
				while ((output = br.readLine()) != null) {
					log.debug(output);
				}

				conn.disconnect();
			}

		}catch (MalformedURLException e) {
			log.error("ReminderJob::execute - Exception while processing Job : "+e.getMessage(),e);
			e.printStackTrace();

		}catch (IOException e) {
			log.error("ReminderJob::execute - Exception while processing Job : "+e.getMessage(),e);
			e.printStackTrace();

		}finally {			
			log.info("ReminderJob::execute - Job ReminderJob Thread Stop ");
		}
	}
	
	@Override
    public void interrupt() throws UnableToInterruptJobException{    	
        stopFlag.set(true);
        Thread thread = runningThread.getAndSet(null);
        if (thread != null)
            thread.interrupt();
    }

	public JobKey getJobKey() {
		return jobKey;
	}

	public void setJobKey(JobKey jobKey) {
		this.jobKey = jobKey;
	}
}
