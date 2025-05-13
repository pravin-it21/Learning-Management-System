package com.cts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.dto.Course;
import com.cts.dto.User;
import com.cts.dto.UserCourseEnrollResponseDTO;
import com.cts.exception.EnrollmentNotFound;
import com.cts.feignclient.CourseClient;
import com.cts.feignclient.UserClient;
import com.cts.model.Enrollment;
import com.cts.repository.EnrollmentRepository;

import jakarta.transaction.Transactional;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
	@Autowired
	EnrollmentRepository repository;

	@Autowired
	UserClient userClient;

	@Autowired
	CourseClient courseClient;

	Logger log = LoggerFactory.getLogger(EnrollmentServiceImpl.class);

	// Save Enrollment Only The User Exist And The Course Exist
	@Override
	public String saveEnrollment(Enrollment enrollment) {
		log.info("In EnrollmentServiceImpl saveEnrollment method...");

		int userId = enrollment.getUserId();
		int courseId = enrollment.getCourseId();
		// Check User Exist By Communication
		Boolean responseUser = userClient.checkUserExist(userId);
		// Check Course Exist By Communication
		Boolean responseCourse = courseClient.checkCourseExist(courseId);
		// Check Enrollment Aldready Exist
		Enrollment enrollmentExist = repository.findByUserIdAndCourseId(userId, courseId);
		if (enrollmentExist != null) {
			return "Enrollment Aldready Exist";
		}
		repository.save(enrollment);
		return "Enrollment Successfully Saved";
	}

	// Used To Update Enrollment

	@Override
	public Enrollment updateEnrollment(Enrollment enrollment) throws EnrollmentNotFound {
		log.info("In EnrollmentServiceImpl updateEnrollment method...");

		int userId = enrollment.getUserId();
		int courseId = enrollment.getCourseId();
		// Check User Exist By Communication

		Boolean responseUser = userClient.checkUserExist(userId);
		// Check Course Exist By Communication

		Boolean responseCourse = courseClient.checkCourseExist(courseId);
		Optional<Enrollment> enrollmentExist = repository.findById(enrollment.getEnrollmentId());
		// Check Enrollment Aldready Exist to Update

		if (enrollmentExist.isPresent())
			return repository.save(enrollment);

		else
			throw new EnrollmentNotFound("Enrollment Id is Invalid...");

	}

	// Used To Delete Enrollments By Id

	@Override
	public String cancelEnrollment(int enrollmentId) throws EnrollmentNotFound {
		log.info("In EnrollmentServiceImpl cancelEnrollment method...");

		Optional<Enrollment> optional = repository.findById(enrollmentId);
		// Check It Is Exist Or Not
		if (optional.isPresent()) {
			repository.delete(repository.findById(enrollmentId).get());
			return "Enrollment Deleted";
		}
		throw new EnrollmentNotFound("Enrollment Id is Invalid");
	}

	// Used To Get All Enrollments
	@Override
	public List<Enrollment> getAllEnrollments() {
		log.info("In EnrollmentServiceImpl cancelEnrollment method...");

		return repository.findAll();
	}

	// Used To Get Enrollments For Particular User

	@Override
	public List<Enrollment> getEnrollmentsByUser(int userId) throws EnrollmentNotFound {
		log.info("In EnrollmentServiceImpl getEnrollmentByUser method...");
		// Check User Exist By Communication
		Boolean responseUser = userClient.checkUserExist(userId);
		List<Enrollment> list = repository.findByUserId(userId);
		// Check Enrollment Exist For that User
		if (list.isEmpty())
			throw new EnrollmentNotFound("No Enrollments For this User Found");
		return list;
	}

	// Used To Get User Details For Particular Course

	@Override
	public List<User> getUsersByCourseId(int courseId) {
		log.info("In EnrollmentServiceImpl getUsersByCourseId method...");
		// Check Course Exist By Communication

		Boolean responseCourse = courseClient.checkCourseExist(courseId);
		List<Enrollment> list = repository.findByCourseId(courseId);
//		if(list.isEmpty()) throw new EnrollmentNotFound("No Enrollments For this Course Found");
		List<User> users = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			Enrollment enroll = list.get(i);
			users.add(userClient.getById(enroll.getUserId()));
		}
		return users;
	}

	// Used to Get Courses For Particular User

	@Override
	public List<Course> getCoursesByUserId(int userId) {
		log.info("In EnrollmentServiceImpl getCoursesByUserId method...");

		// Check User Exist By Communication

		Boolean responseUser = userClient.checkUserExist(userId);
		List<Enrollment> list = repository.findByUserId(userId);
//		if(list.isEmpty()) throw new EnrollmentNotFound("No Enrollments For this User Found");
		List<Course> courses = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			Enrollment enroll = list.get(i);
			courses.add(courseClient.getCourse(enroll.getCourseId()));
		}

		return courses;
	}

	// Used To Get User,Course,Enroll Details By Enrollment Id

	@Override
	public UserCourseEnrollResponseDTO getEnrollment(int enrollmentId) throws EnrollmentNotFound {
		log.info("In EnrollmentServiceImpl getEnrollment method...");

		Optional<Enrollment> optional = repository.findById(enrollmentId);
		if (optional.isPresent()) {
			Enrollment enrollment = optional.get();
			int userId = enrollment.getUserId();
			int courseId = enrollment.getCourseId();
			// Communication with User Service
			User user = userClient.getById(userId);
			// Communication with Course service
			Course course = courseClient.getCourse(courseId);
			UserCourseEnrollResponseDTO responseDTO = new UserCourseEnrollResponseDTO(user, course, enrollment);
			return responseDTO;
		}
		throw new EnrollmentNotFound("Enrollment Id is Invalid");
	}

	// Used To Delete Enrollments For a Particular Course

	@Override
	@Transactional
	public String cancelEnrollmentsCourseId(int courseId) {
		log.info("In EnrollmentServiceImpl cancelEnrollmentsCourseId method...");

//		Boolean responseCourse = courseClient.checkCourseExist(courseId);
//		List<Enrollment> list = repository.findByCourseId(courseId);
//		if(list.isEmpty()) throw new EnrollmentNotFound("No Enrollments For this Course Found");
		repository.deleteByCourseId(courseId);
		return "All the Enrollments For this Course Deleted";
	}

	// Used To Check Whether the User Aldready Registered For A Particular Course
	@Override
	public Boolean checkEnrollmentByUserIdAndCourseId(int userId, int courseId) throws EnrollmentNotFound {
		log.info("In EnrollmentServiceImpl checkEnrollmentByUserIdAndCourseId method...");

		Boolean response = repository.existsByUserIdAndCourseId(userId, courseId);
		if (response)
			return response;
		else
			throw new EnrollmentNotFound("User Not Enrolled For This Course");
	}

}
