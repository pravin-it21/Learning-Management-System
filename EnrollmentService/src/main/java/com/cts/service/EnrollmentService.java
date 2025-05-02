package com.cts.service;

import java.util.List;

import com.cts.dto.Course;
import com.cts.dto.EnrollCourse;
import com.cts.model.Enrollment;

public interface EnrollmentService {
	public abstract String enrollStudent(EnrollCourse enrollCourse);

	public abstract Enrollment updateEnrollment(int userId, int courseId);

	public abstract void cancelEnrollment(int enrollmentId);

	public abstract List<Enrollment> getEnrollmentsByUser(int userId);

	public abstract List<Course> getEnrollmentsByCourse(int courseId);

	public boolean checkEnrollmentStatus(int userId, int courseId);

	public abstract Enrollment getCourse(int enrollId);

	public abstract List<Enrollment> getAllCourses();
}
