package com.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.dto.AdminUserProgressDTO;
import com.cts.dto.CourseDTO;
import com.cts.dto.UserDTO;
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

	@GetMapping("/fetchProgressByUserId/{uid}")
	public UserDTO getProgressByUserId(@PathVariable("uid") int userId) {
		return service.getProgressByUserId(userId);
	}
	
//	@GetMapping("/fetchAllProgress")
//	public List<UserDTO> getAllProgress() {
//		return service.getAllProgress();
//	}
	@GetMapping("/fetchAllProgress")
    public List<AdminUserProgressDTO> getAllProgressWithUserDetails() {
		return service.getAllProgressWithUserDetails();
	}

}
