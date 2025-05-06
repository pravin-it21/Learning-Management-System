package com.cts.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.exception.CourseNotFound;
import com.cts.model.Course;
import com.cts.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService {
	@Autowired
	CourseRepository repository;

	@Override
	public String createCourse(Course course) {
		// log.info("In CourseServiceImpl createCourse method...");
		Course crs = repository.save(course);
		if (crs != null)
			return "Course Saved Successfully";
		else
			return "Course Not Saved";
	}

	@Override
	public Course updateCourse(Course course) {
		// log.info("In CourseServiceImpl updateCourse method...");
		return repository.save(course);
	}

	@Override
	public String deleteCourse(int courseId) {
		// log.info("In CourseServiceImpl deleteCourse method...");
		repository.deleteById(courseId);
		return "Course Deleted";
	}

	@Override
	public Course getCourse(int courseId) throws CourseNotFound {

		Optional<Course> optional = repository.findById(courseId);
		if (optional.isPresent())
			return optional.get();
		else
			throw new CourseNotFound("Course Id is Invalid...");

	}

	@Override
	public List<Course> getAllCourses() {
		return repository.findAll();
	}

	@Override
	public Boolean checkCourseExist(int courseId) throws CourseNotFound {		
		Boolean response = repository.existsById(courseId);
		if(response) {
			return response;
		}
		else {
			throw new CourseNotFound("Course Id is Invalid....");
		}
	}

}
