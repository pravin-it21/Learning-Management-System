package com.cts.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.exception.CourseNotFound;
import com.cts.feignclient.EnrollmentClient;
import com.cts.feignclient.QuizClient;
import com.cts.model.Course;
import com.cts.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService {
	@Autowired
	CourseRepository repository;
	@Autowired
	EnrollmentClient enrollmentClient;

	@Autowired
	QuizClient quizClient;

	Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);

	// Used To Create Course
	@Override
	public String createCourse(Course course) {
		log.info("In CourseServiceImpl createCourse method...");
		Optional<Course> optional = repository.findById(course.getCourseId());
		if (optional.isPresent())
			return "Course Aldready Exist";
		Course crs = repository.save(course);
		if (crs != null)
			return "Course Saved Successfully";
		else
			return "Course Not Saved";
	}

	// Used To Update Course
	@Override
	public Course updateCourse(Course course) throws CourseNotFound {
		log.info("In CourseServiceImpl updateCourse method...");
		Optional<Course> optional = repository.findById(course.getCourseId());
		if (optional.isPresent())
			return repository.save(course);
		else
			throw new CourseNotFound("Course Id is Invalid...");

	}

	// Used To Delete Course By CourseId
	@Override
	public String deleteCourse(int courseId) throws CourseNotFound {
		log.info("In CourseServiceImpl deleteCourse method...");
		Optional<Course> optional = repository.findById(courseId);
		if (optional.isPresent()) {
			repository.deleteById(courseId);
			enrollmentClient.cancelEnrollmentsByCourseId(courseId);
			quizClient.deleteQuizByCourseId(courseId);
			return "Course Deleted";
		} else {
			throw new CourseNotFound("Course Id is Invalid...");
		}

	}

	// Used To Get CourseBy CourseId
	@Override
	public Course getCourse(int courseId) throws CourseNotFound {
		log.info("In CourseServiceImpl getCourse method...");

		Optional<Course> optional = repository.findById(courseId);
		if (optional.isPresent())
			return optional.get();
		else
			throw new CourseNotFound("Course Id is Invalid...");

	}

	// Used To GetAll Courses
	@Override
	public List<Course> getAllCourses() {
		log.info("In CourseServiceImpl getAllCpourses method...");

		return repository.findAll();
	}

	// Used To Check Whether the Course Exist in DataBase
	@Override
	public Boolean checkCourseExist(int courseId) throws CourseNotFound {
		log.info("In CourseServiceImpl checkCourseExist method...");

		Boolean response = repository.existsById(courseId);
		if (response) {
			return response;
		} else {
			throw new CourseNotFound("Course Id is Invalid....");
		}
	}

	@Override
	public List<Course> getCoursesByInstructorId(int instructorId) {
		// TODO Auto-generated method stub
		return repository.findByInstructorId(instructorId);
	}

}
