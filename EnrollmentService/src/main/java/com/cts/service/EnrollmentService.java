package com.cts.service;

import java.util.List;

import com.cts.dto.EnrollCourseRequestDTO;
import com.cts.model.Enrollment;

public interface EnrollmentService {
	public abstract String saveEnrollment(EnrollCourseRequestDTO enrollCourse);

	public abstract Enrollment updateEnrollment(EnrollCourseRequestDTO enrollCourse);

	public abstract String cancelEnrollment(int enrollmentId);

	public abstract List<Enrollment> getAllEnrollments();

	public abstract List<Enrollment> getEnrollmentsByUser(int userId);

	public abstract List<Enrollment> getEnrollmentsByCourse(int courseId);

	public abstract Enrollment getEnrollment(int enrollmentId);
}
