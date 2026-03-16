package com.jai.scheduler.service;

import com.jai.scheduler.entity.Job;
import com.jai.scheduler.repository.JobRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JobServiceTest {

    @Autowired
    private JobService jobService;

    @Autowired
    private JobRepository jobRepository;

    @Test
    void testCreateJob() {

        Job job = new Job();
        job.setJobName("test-job");
        job.setCronExpression("test");

        Job savedJob = jobService.createJob(job);

        assertNotNull(savedJob.getId());
        assertEquals("SCHEDULED", savedJob.getStatus());
    }
}