 package com.cts.service;

import java.util.List;

import com.cts.model.Course;

public interface CourseService {
	public abstract String createCourse(Course course);

	public abstract Course updateCourse(Course Course);

	public abstract String deleteCourse(int courseId);

	public abstract Course getCourse(int courseId);

	public abstract List<Course> getAllCourses();
	
	public abstract Boolean checkCourseExist(int courseId);

	
}
