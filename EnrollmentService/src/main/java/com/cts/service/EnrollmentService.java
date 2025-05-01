package com.cts.service;

import java.util.List;

import com.cts.model.Enrollment;

public interface EnrollmentService {
	public abstract String createCourse(Enrollment enroll);

	public abstract Enrollment updateCourse(Enrollment enroll);

	public abstract String deleteCourse(int enrollId);

	public abstract Enrollment getCourse(int enrollId);

	public abstract List<Enrollment> getAllCourses();
}
