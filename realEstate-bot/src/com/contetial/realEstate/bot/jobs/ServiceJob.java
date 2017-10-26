package com.contetial.realEstate.bot.jobs;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.quartz.InterruptableJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.UnableToInterruptJobException;

public class ServiceJob implements Job, InterruptableJob{
	
    AtomicReference<Thread> runningThread = new AtomicReference<Thread>();
    AtomicBoolean stopFlag = new AtomicBoolean(false);    
    private JobKey jobKey= null;
   
    @Override
    public void execute(JobExecutionContext context)
        throws JobExecutionException{
    	
        this.runningThread.set(Thread.currentThread());
        setJobKey(context.getJobDetail().getKey());
        try {
          while(!stopFlag.get()) {
            System.out.println("********** Doing something Important *********");
            if (stopFlag.get()) break;
            // do more
          }
        } finally { runningThread.set(null); }
    }

    @Override
    public void interrupt()
        throws UnableToInterruptJobException{    	
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