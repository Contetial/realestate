package com.contetial.realEstate.bot.jobcontroller;


import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.contetial.realEstate.bot.jobprocessor.JobProcessor;
import com.contetial.realEstate.utility.propReader.ReadConfigurations;

public class JobRunner{
	
	/* Get actual class name to be printed on */
	static Logger log = Logger.getLogger(JobRunner.class.getName());
	private SchedulerFactory sf;
	private Scheduler scheduler;
	
	public static void main(String[] args) throws Exception{
		JobRunner  runner  = new JobRunner();
		Properties props = new Properties();
		props.setProperty(StdSchedulerFactory.PROP_SCHED_SKIP_UPDATE_CHECK,"true");
		props.setProperty("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");
		props.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
		props.setProperty("org.quartz.threadPool.threadCount", "4");
		runner.sf = new StdSchedulerFactory(props);
		runner.startSchedulers();
	}
	
	public void startSchedulers() throws Exception{
		
		boolean oneTimeSet = true;
		Set<JobKey> jobKeys=new HashSet<JobKey>();
		
		while(oneTimeSet){
			
			String schedulerStatus=
					ReadConfigurations.getInstance("./conf/realEstateBot.properties")
					.getProps().getProperty("scheduler.jobs.status");
			
			if("stop".equalsIgnoreCase(schedulerStatus)){

				log.info("JobRunner has directed to stop. "
						+ "Please change property value scheduler.jobs.status=start to start the scheduler again");
				oneTimeSet = false;
				if(null!=scheduler && !jobKeys.isEmpty()){
					for(JobKey jobKey:jobKeys){
						scheduler.interrupt(jobKey);
					}
					scheduler.shutdown();
				}
				log.info("JobRunner Ended");
			}else{
				if(null==scheduler){
					log.info("++++++++++++ JobRunner Started for the first time +++++++++++++");					
					scheduler = sf.getScheduler();
					JobProcessor processor = new JobProcessor();
					jobKeys.add(processor.scheduleWishingJob(scheduler));
					jobKeys.add(processor.scheduleReminderJob(scheduler));
				}
				Thread.sleep(4 * 1000L);
				//log.debug("################## Looping #################");
			}
		}
	}	
}
