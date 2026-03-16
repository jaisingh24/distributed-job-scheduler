package com.jai.scheduler.repository;

import com.jai.scheduler.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByStatusAndNextRunTimeBefore(String status, LocalDateTime time);

}