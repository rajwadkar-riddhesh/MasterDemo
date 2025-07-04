package com.example.demomaster.batch;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JobNotification implements JobExecutionListener {

    private Logger logger = LoggerFactory.getLogger(JobNotification.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("Job is started");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus()== BatchStatus.COMPLETED) {
            logger.info("Job completed");
        }
    }
}
