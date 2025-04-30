package com.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {

}
