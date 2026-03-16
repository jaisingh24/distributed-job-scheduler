package com.jai.scheduler.service;

import com.jai.scheduler.entity.Job;
import com.jai.scheduler.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class JobService {
    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job createJob(Job job) {

        job.setCreatedAt(LocalDateTime.now());

        // Job initially scheduled
        job.setStatus("SCHEDULED");

        // Run after 10 seconds (temporary test)
        job.setNextRunTime(LocalDateTime.now().plusSeconds(10));
        job.setRetryCount(0);
        job.setMaxRetries(3);

        return jobRepository.save(job);
    }
}
