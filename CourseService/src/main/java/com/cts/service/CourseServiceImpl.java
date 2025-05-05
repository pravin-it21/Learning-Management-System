package com.cts.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.model.Course;
import com.cts.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService {
	@Autowired
	CourseRepository repository;
	@Override
	public String createCourse(Course course) {
		repository.save(course);
		return "Course Saved";
	}

	@Override
	public Course updateCourse(Course course) {
		return repository.save(course);
	}

	@Override
	public String deleteCourse(int courseId) {
		Optional<Course> option = repository.findById(courseId);
		repository.delete(option.get());
		return "Course Deleted";
	}

	@Override
	public Course getCourse(int courseId) {
		return repository.findById(courseId).get();
	}

	@Override
	public List<Course> getAllCourses() {
		return repository.findAll();
	}

	@Override
	public Boolean checkCourseExist(int courseId) {
		return repository.existsById(courseId);
	}

}
