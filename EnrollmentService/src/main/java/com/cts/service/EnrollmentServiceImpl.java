package com.cts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.dto.EnrollCourseRequestDTO;
import com.cts.model.Enrollment;
import com.cts.repository.EnrollmentRepository;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
	@Autowired
	EnrollmentRepository repository;

	@Override
	public String saveEnrollment(EnrollCourseRequestDTO enrollCourse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enrollment updateEnrollment(EnrollCourseRequestDTO enrollCourse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String cancelEnrollment(int enrollmentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Enrollment> getAllEnrollments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Enrollment> getEnrollmentsByUser(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Enrollment> getEnrollmentsByCourse(int courseId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enrollment getEnrollment(int enrollmentId) {
		// TODO Auto-generated method stub
		return null;
	}

}
