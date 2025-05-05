package com.cts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.dto.Course;
import com.cts.dto.User;
import com.cts.dto.UserCourseEnrollResponseDTO;
import com.cts.feignclient.CourseClient;
import com.cts.feignclient.UserClient;
import com.cts.model.Enrollment;
import com.cts.repository.EnrollmentRepository;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
	@Autowired
	EnrollmentRepository repository;
	
	@Autowired
	UserClient userClient;
	
	@Autowired
	CourseClient courseClient;

	@Override
	public String saveEnrollment(Enrollment enrollment) {		
	        if (!userClient.checkUserExist(enrollment.getUserId())) {
	            return "Error: User ID " + enrollment.getUserId() + " does not exist!";
	        }

	        if (!courseClient.checkCourseExist(enrollment.getCourseId())) {
	            return "Error: Course ID " + enrollment.getCourseId() + " does not exist!";
	        }

		repository.save(enrollment);
		return "Enrollment Successfully Saved";
	}

	@Override
	public Enrollment updateEnrollment(Enrollment enrollment) {
		return repository.save(enrollment);
	}

	@Override
	public String cancelEnrollment(int enrollmentId) {

		repository.delete(repository.findById(enrollmentId).get());
		return "Enrollment Deleted";
	}

	@Override
	public List<Enrollment> getAllEnrollments() {
		return repository.findAll();
	}

	@Override
	public List<Enrollment> getEnrollmentsByUser(int userId) {
		return repository.findByUserId(userId);
	}

	@Override
	public List<Enrollment> getEnrollmentsByCourse(int courseId) {
		return repository.findByCourseId(courseId);
	}

	@Override
	public UserCourseEnrollResponseDTO getEnrollment(int enrollmentId) {
		Enrollment enrollment = repository.findById(enrollmentId).get();
		int userId = enrollment.getUserId();
		int courseId = enrollment.getCourseId();
		User user = userClient.getById(userId);
		Course course = courseClient.getCourse(courseId);
		UserCourseEnrollResponseDTO responseDTO = new UserCourseEnrollResponseDTO(user, course, enrollment);
		return responseDTO;
	}

}
