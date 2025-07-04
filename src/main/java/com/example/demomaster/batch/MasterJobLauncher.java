package com.example.demomaster.batch;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MasterJobLauncher {

    private final JobLauncher jobLauncher;
    private final Job job;

    @Autowired
    public MasterJobLauncher(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @Scheduled(initialDelay = 5000)
    public void run() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        System.out.println("Launching job");

        JobParameters params = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .addString("filePath", "src/main/resources/india_pincodes_100_within_same_city.xlsx")
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(job,params);

        System.out.println("Job Execution Status: " + jobExecution.getStatus());
    }
}
