package com.contetial.realEstate.bot.jobprocessor;

import java.io.IOException;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.contetial.realEstate.bot.jobs.ReminderJob;
import com.contetial.realEstate.bot.jobs.WishingJob;
import com.contetial.realEstate.utility.propReader.ReadConfigurations;

public class JobProcessor{
	static Logger log = Logger.getLogger(JobProcessor.class.getName());
	
	public JobKey scheduleWishingJob(Scheduler schedulerLocal) throws SchedulerException, IOException {
		/*WishingJobProcessor Schedule */
		
		JobDetail job = JobBuilder.newJob(WishingJob.class)
				.withIdentity("WishingJob", "group1").build();
		
		String atHour=
				ReadConfigurations.getInstance("./conf/realEstateBot.properties")
				.getProps().getProperty("scheduler.repeat.atHour");
		String atMin=
				ReadConfigurations.getInstance("./conf/realEstateBot.properties")
				.getProps().getProperty("scheduler.repeat.atMinute");
		/*String repeatPattern=
				ReadConfigurations.getInstance("./conf/realEstateBot.properties")
				.getProps().getProperty("scheduler.jobs.defaultprocess.trigger.expression");
		.withSchedule(CronScheduleBuilder.cronSchedule(repeatPattern))*/

		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("WishingJobTrigger", "group1")
				.withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(
							Integer.valueOf(atHour), Integer.valueOf(atMin)))		
				.startAt(Calendar.getInstance().getTime())
				.build();		
		
		//schedule it
		schedulerLocal.scheduleJob(job, trigger);
		schedulerLocal.start();
		
		return job.getKey();
	}
	
	public JobKey scheduleReminderJob(Scheduler schedulerLocal) throws SchedulerException, IOException {
		/*WishingJobProcessor Schedule */
		
		JobDetail job = JobBuilder.newJob(ReminderJob.class)
				.withIdentity("ReminderJob", "group2").build();
		
		String atHour=
				ReadConfigurations.getInstance("./conf/realEstateBot.properties")
				.getProps().getProperty("scheduler.repeat.atHour");
		String atMin=
				ReadConfigurations.getInstance("./conf/realEstateBot.properties")
				.getProps().getProperty("scheduler.repeat.atMinute");
		/*String repeatPattern=
				ReadConfigurations.getInstance("./conf/realEstateBot.properties")
				.getProps().getProperty("scheduler.jobs.defaultprocess.trigger.expression");
		.withSchedule(CronScheduleBuilder.cronSchedule(repeatPattern))*/

		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("ReminderJobTrigger", "group2")
				.withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(
							Integer.valueOf(atHour), Integer.valueOf(atMin)))		
				.startAt(Calendar.getInstance().getTime())
				.build();		
		
		//schedule it
		schedulerLocal.scheduleJob(job, trigger);
		schedulerLocal.start();
		
		return job.getKey();
	}

	
	/*public void printMemoryUses(){
		int mb = 1024*1024;
		//Getting the runtime reference from system
	    Runtime runtime = Runtime.getRuntime();
		log.info("##### Heap utilization statistics [MB] #####");
		log.info("Used Memory:" + ((runtime.totalMemory() - runtime.freeMemory()) / mb) +" MB");
		log.info("Free Memory:" + (runtime.freeMemory() / mb) +" MB");
		log.info("Total Memory:" + (runtime.totalMemory() / mb) +" MB");
		log.info("Max Memory:" + (runtime.maxMemory() / mb) +" MB");
		
	}*/
}
