package com.example.demomaster.batch;

import com.example.demomaster.dto.PincodeCreateDTO;
import com.example.demomaster.service.PincodeService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class MasterConfig {

    @Autowired
    private MasterWriter writer;

    @Bean
    public Job jobConfig(JobRepository jobRepository,Step step ){
        return new  JobBuilder("jobconfig",jobRepository)
                .start(step)
                .build();

    }

    @Bean
    public Step step(JobRepository jobRepository,
                     PlatformTransactionManager platformTransactionManager,
                     ItemReader<PincodeCreateDTO> itemReader,
                     ItemWriter<PincodeCreateDTO> itemWriter){

        return new StepBuilder("step", jobRepository)
                .<PincodeCreateDTO, PincodeCreateDTO>chunk(10, platformTransactionManager)
                .reader(itemReader)
                .writer(itemWriter)
                .build();
    }
}
