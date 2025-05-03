package com.cts.service;

import java.util.List;

import com.cts.model.Enrollment;

public interface EnrollmentService {
	public abstract String saveEnrollment(Enrollment enrollment);

	public abstract Enrollment updateEnrollment(Enrollment enrollment);

	public abstract String cancelEnrollment(int enrollmentId);

	public abstract List<Enrollment> getAllEnrollments();

	public abstract List<Enrollment> getEnrollmentsByUser(int userId);

	public abstract List<Enrollment> getEnrollmentsByCourse(int courseId);

	public abstract Enrollment getEnrollment(int enrollmentId);
}
