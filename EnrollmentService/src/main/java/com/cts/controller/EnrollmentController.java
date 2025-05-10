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

import com.cts.dto.Course;
import com.cts.dto.User;
import com.cts.dto.UserCourseEnrollResponseDTO;
import com.cts.exception.EnrollmentNotFound;
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
	public Enrollment updateEnrollment(@RequestBody Enrollment enrollment) throws EnrollmentNotFound {
		return service.updateEnrollment(enrollment);
	}

	@GetMapping("/fetchById/{eid}")
	public UserCourseEnrollResponseDTO getEnrollment(@PathVariable("eid") int enrollmentId) throws EnrollmentNotFound {
		return service.getEnrollment(enrollmentId);
	}

	@GetMapping("/fetchByUser/{uid}")
	public List<Enrollment> getEnrollmentsByUser(@PathVariable("uid") int userId) throws EnrollmentNotFound {
		return service.getEnrollmentsByUser(userId);
	}

	@GetMapping("/fetchUsersByCourseId/{cid}")
	public List<User> getUsersByCourseId(@PathVariable("cid") int courseId)  {
		return service.getUsersByCourseId(courseId);
	}
	
	@GetMapping("/fetchCoursesByUserId/{uid}")
	public List<Course> getCoursesByUserId(@PathVariable("uid") int userId) {
		return service.getCoursesByUserId(userId);
	}

	@GetMapping("/fetchAll")
	public List<Enrollment> getAllEnrollments() {
		return service.getAllEnrollments();
	}

	@DeleteMapping("/cancel/{eid}")
	public String cancelEnrollment(@PathVariable("eid") int enrollmentId) throws EnrollmentNotFound {
		return service.cancelEnrollment(enrollmentId);
	}
	
	@DeleteMapping("/cancelEnrollmentsByCourseId/{cid}")
	public String cancelEnrollmentsByCourseId(@PathVariable("cid") int courseId) {
		return service.cancelEnrollmentsCourseId(courseId);
	}
	
	@GetMapping("checkUserEnrollCourse/{uid}/{cid}")
	public Boolean checkEnrollmentByUserIdAndCourseId(@PathVariable("uid") int userId,@PathVariable("cid") int courseId) throws EnrollmentNotFound {
		return service.checkEnrollmentByUserIdAndCourseId(userId,courseId);
	}

}
