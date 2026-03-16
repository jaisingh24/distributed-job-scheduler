package com.jai.scheduler.repository;

import com.jai.scheduler.entity.JobExecution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobExecutionRepository extends JpaRepository<JobExecution, Long> {
}