package com.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.model.Course;
import com.cts.model.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {

}
