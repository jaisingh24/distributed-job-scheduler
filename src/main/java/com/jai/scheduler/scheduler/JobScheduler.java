package com.jai.scheduler.scheduler;

import com.jai.scheduler.entity.Job;
import com.jai.scheduler.repository.JobRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.List;



@Component
public class JobScheduler {

    private final JobRepository jobRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public JobScheduler(JobRepository jobRepository,
                        RedisTemplate<String, Object> redisTemplate) {
        this.jobRepository = jobRepository;
        this.redisTemplate = redisTemplate;
    }

    @Scheduled(fixedRate = 5000)
    public void checkJobs() {

        LocalDateTime now = LocalDateTime.now();

        List<Job> jobs =
                jobRepository.findByStatusAndNextRunTimeBefore("SCHEDULED", now);

        for (Job job : jobs) {

            System.out.println("Pushing job to queue: " + job.getJobName());

            redisTemplate.opsForList().leftPush("jobQueue", job);

            // Update job status
            job.setStatus("QUEUED");

            jobRepository.save(job);
        }
    }
}