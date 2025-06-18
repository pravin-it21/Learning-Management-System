package com.cts.service;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.exception.CourseNotFound;
import com.cts.model.Course;

public interface CourseService {
	public abstract String createCourse(Course course);

	public abstract Course updateCourse(Course Course) throws CourseNotFound;

	public abstract String deleteCourse(int courseId) throws CourseNotFound;

	public abstract Course getCourse(int courseId) throws CourseNotFound;

	public abstract List<Course> getAllCourses();

	public abstract Boolean checkCourseExist(int courseId) throws CourseNotFound;
	
	public abstract List<Course> getCoursesByInstructorId(int instructorId);

}
