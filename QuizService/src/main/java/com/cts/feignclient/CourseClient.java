package com.cts.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "COURSESERVICE",path="/course")

public interface CourseClient {
	@GetMapping("/checkCourseExist/{cid}")
	public Boolean checkCourseExist(@PathVariable("cid") int courseId );
}
