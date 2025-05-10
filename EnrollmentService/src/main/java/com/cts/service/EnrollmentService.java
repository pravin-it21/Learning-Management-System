package com.cts.service;

import java.util.List;

import com.cts.dto.Course;
import com.cts.dto.User;
import com.cts.dto.UserCourseEnrollResponseDTO;
import com.cts.exception.EnrollmentNotFound;
import com.cts.model.Enrollment;

public interface EnrollmentService {
	public abstract String saveEnrollment(Enrollment enrollment);

	public abstract Enrollment updateEnrollment(Enrollment enrollment) throws EnrollmentNotFound;

	public abstract String cancelEnrollment(int enrollmentId) throws EnrollmentNotFound;

	public abstract List<Enrollment> getAllEnrollments();

	public abstract List<Enrollment> getEnrollmentsByUser(int userId) throws EnrollmentNotFound ;

	public abstract List<User> getUsersByCourseId(int courseId);

	public abstract UserCourseEnrollResponseDTO getEnrollment(int enrollmentId) throws EnrollmentNotFound;

	public abstract List<Course> getCoursesByUserId(int userId);

	public abstract String cancelEnrollmentsCourseId(int courseId)throws EnrollmentNotFound ;

	public abstract Boolean checkEnrollmentByUserIdAndCourseId(int userId, int courseId) throws EnrollmentNotFound;
}
