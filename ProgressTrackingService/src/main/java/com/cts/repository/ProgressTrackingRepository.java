package com.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.model.Progress;

public interface ProgressTrackingRepository extends JpaRepository<Progress, Integer> {

}
