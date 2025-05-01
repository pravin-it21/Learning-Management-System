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

import com.cts.model.Enrollment;
import com.cts.service.EnrollmentService;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {
	@Autowired
	EnrollmentService service;
	
	@PostMapping("/create")
	public String createCourse(@RequestBody Enrollment  enrollment) {
		return service.createCourse(enrollment);
	}

	@PutMapping("/update")
	public Enrollment updateCourse(@RequestBody Enrollment enrollment) {
		return service.updateCourse(enrollment);
	}

	@GetMapping("/fetchById/{cid}")
	public Enrollment getCourse(@PathVariable("cid") int courseId) {
		return service.getCourse(courseId);
	}

	@GetMapping("/fetchAll")

	public List<Enrollment> getAllCourses() {
		return service.getAllCourses();
	}
	
	@DeleteMapping("/delete/{cid}")
	public String deleteCourse(@PathVariable("cid") int courseId) {
		return service.deleteCourse(courseId);
	}
	
	

}
