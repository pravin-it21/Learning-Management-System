package com.cts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.model.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
	public abstract List<Enrollment> findByUserId(int userId);

	public abstract List<Enrollment> findByCourseId(int courseId);

	public abstract void deleteByCourseId(int courseId);
	
	public abstract Enrollment findByUserIdAndCourseId(int userId,int courseId);

}
