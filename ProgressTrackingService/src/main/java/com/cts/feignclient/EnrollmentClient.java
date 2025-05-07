package com.cts.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.dto.CourseDTO;

@FeignClient(name="ENROLLMENTSERVICE",path="/enroll")
public interface EnrollmentClient {
	@GetMapping("/fetchCoursesByUserId/{uid}")
	public List<CourseDTO> getCoursesByUserId(@PathVariable("uid") int userId );
}
