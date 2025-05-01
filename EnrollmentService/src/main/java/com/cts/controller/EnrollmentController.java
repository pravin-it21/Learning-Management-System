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

import com.cts.model.Course;
import com.cts.service.CourseService;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {
	@Autowired
	CourseService service;
	
	@PostMapping("/create")
	public String createCourse(@RequestBody Course  course) {
		return service.createCourse(course);
	}

	@PutMapping("/update")
	public Course updateCourse(@RequestBody Course course) {
		return service.updateCourse(course);
	}

	@GetMapping("/fetchById/{cid}")
	public Course getCourse(@PathVariable("cid") int courseId) {
		return service.getCourse(courseId);
	}

	@GetMapping("/fetchAll")

	public List<Course> getAllCourses() {
		return service.getAllCourses();
	}
	
	@DeleteMapping("/delete/{cid}")
	public String deleteCourse(@PathVariable("cid") int courseId) {
		return service.deleteCourse(courseId);
	}
	
	

}
