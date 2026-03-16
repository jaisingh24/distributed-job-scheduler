package com.jai.scheduler.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.jai.scheduler.entity.Job;
import com.jai.scheduler.repository.JobRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JobRepositoryTest {

    @Autowired
    private JobRepository jobRepository;

  //private Job job;
    @Test
    void testSaveJob() {

        Job job = new Job();
        job.setJobName("Backend Developer");

        Job savedJob = jobRepository.save(job);

        assertNotNull(savedJob.getId());
        assertEquals("Backend Developer", savedJob.getJobName());
    }
}