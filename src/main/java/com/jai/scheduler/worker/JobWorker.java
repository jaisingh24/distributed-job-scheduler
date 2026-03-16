package com.jai.scheduler.worker;

import com.jai.scheduler.entity.Job;
import com.jai.scheduler.entity.JobExecution;
import com.jai.scheduler.repository.JobExecutionRepository;
import com.jai.scheduler.repository.JobRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.time.LocalDateTime;

@Component
public class JobWorker {

    private final RedisTemplate<String, Object> redisTemplate;
    private final JobRepository jobRepository;
    private final JobExecutionRepository jobExecutionRepository;

    public JobWorker(RedisTemplate<String, Object> redisTemplate,
                     JobRepository jobRepository,
                     JobExecutionRepository jobExecutionRepository) {

        this.redisTemplate = redisTemplate;
        this.jobRepository = jobRepository;
        this.jobExecutionRepository = jobExecutionRepository;
    }

    @PostConstruct
    public void startWorker() {

        new Thread(() -> {

            while (true) {

                Job job = (Job) redisTemplate.opsForList().rightPop("jobQueue");

                if (job != null) {

                    System.out.println("Executing job: " + job.getJobName());

                    JobExecution execution = new JobExecution();

                    execution.setJobId(job.getId());
                    execution.setStartTime(LocalDateTime.now());
                    execution.setStatus("RUNNING");

                    execution = jobExecutionRepository.save(execution);

                    job.setStatus("RUNNING");
                    jobRepository.save(job);

                    try {

                        Thread.sleep(2000);

// simulate random failure
                        if (Math.random() < 0.7) {
                            throw new RuntimeException("Simulated job failure");
                        }
                        execution.setEndTime(LocalDateTime.now());
                        execution.setStatus("SUCCESS");

                        jobExecutionRepository.save(execution);

                        job.setStatus("COMPLETED");
                        jobRepository.save(job);

                        System.out.println("Job finished: " + job.getJobName());

                    } catch (Exception e) {

                        execution.setEndTime(LocalDateTime.now());
                        execution.setStatus("FAILED");

                        jobExecutionRepository.save(execution);

                        job.setStatus("FAILED");
                        jobRepository.save(job);

                        // CALL RETRY LOGIC HERE
                        handleRetry(job);

                        e.printStackTrace();
                    }
                    }
                }


        }).start();
    }

    private void handleRetry(Job job) {

        job.setRetryCount(job.getRetryCount() + 1);

        System.out.println("Retry count: " + job.getRetryCount());

        if (job.getRetryCount() <= job.getMaxRetries()) {

            System.out.println("Retrying job: " + job.getJobName());

            redisTemplate.opsForList().leftPush("jobQueue", job);

        } else {

            System.out.println("Job moved to Dead Letter Queue");

            redisTemplate.opsForList().leftPush("deadLetterQueue", job);
        }

        jobRepository.save(job);
    }
}
