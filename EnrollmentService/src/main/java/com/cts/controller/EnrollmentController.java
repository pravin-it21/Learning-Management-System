package com.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.dto.UserCourseEnrollResponseDTO;
import com.cts.model.Enrollment;
import com.cts.service.EnrollmentService;

@RestController
@RequestMapping("/enroll")
public class EnrollmentController {
	@Autowired
	EnrollmentService service;

	@PostMapping("/save")
	public String saveEnrollment(@RequestBody Enrollment enrollment) {
		return service.saveEnrollment(enrollment);
	}

	@PutMapping("/update")
	public Enrollment updateEnrollment(@RequestBody Enrollment enrollment) {
		return service.updateEnrollment(enrollment);
	}

	@GetMapping("/fetchById/{eid}")
	public UserCourseEnrollResponseDTO getEnrollment(@PathVariable("eid") int enrollmentId) {
		return service.getEnrollment(enrollmentId);
	}

	@GetMapping("/fetchByUser/{uid}")
	public List<Enrollment> getEnrollmentsByUser(@PathVariable("uid") int userId) {
		return service.getEnrollmentsByUser(userId);
	}

	@GetMapping("/fetchByCourse/{cid}")
	public List<Enrollment> getEnrollmentsByCourse(@PathVariable("cid") int courseId) {
		return service.getEnrollmentsByCourse(courseId);
	}

	@GetMapping("/fetchAll")
	public List<Enrollment> getAllEnrollments() {
		return service.getAllEnrollments();
	}

	@DeleteMapping("/cancel/{eid}")
	public String cancelEnrollment(@PathVariable("eid") int enrollmentId) {
		return service.cancelEnrollment(enrollmentId);
	}

}
