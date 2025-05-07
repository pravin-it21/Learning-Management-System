package com.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.dto.CourseDTO;
import com.cts.service.ProgressTrackingService;

@RestController
@RequestMapping("/progress")
public class ProgressTrackingController {
	@Autowired
	ProgressTrackingService service;
	
	@GetMapping("/fetchByUserId/{uid}")
	public List<CourseDTO> getCourseByUserId(@PathVariable("uid") int userId) {
		return service.getCourseByUserId(userId);
	}
}
