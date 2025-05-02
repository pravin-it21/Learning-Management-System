package com.cts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.dto.Course;
import com.cts.dto.EnrollCourse;
import com.cts.model.Enrollment;
import com.cts.repository.EnrollmentRepository;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
	@Autowired
	EnrollmentRepository repository;
	
	
	@Override
	public String enrollStudent(EnrollCourse enrollCourse) {
		
		repository.save(enrollCourse.getEmp());
		String response = departmentClient.saveDepartment(enrollCourse.getDept());
		if (response.equals("Department Saved"))
			return "Employee Saved !!!";
		else
			return "Something went wrong!!!";
		
		return null;
	}

	@Override
	public Enrollment updateEnrollment(int userId, int courseId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cancelEnrollment(int enrollmentId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Enrollment> getEnrollmentsByUser(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Course> getEnrollmentsByCourse(int courseId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkEnrollmentStatus(int userId, int courseId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Enrollment getCourse(int enrollId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Enrollment> getAllCourses() {
		// TODO Auto-generated method stub
		return null;
	}

}
