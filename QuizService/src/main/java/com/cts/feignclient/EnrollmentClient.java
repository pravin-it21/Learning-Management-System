package com.cts.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ENROLLMENTSERVICE", path = "/enroll")
public interface EnrollmentClient {

	@GetMapping("checkUserEnrollCourse/{uid}/{cid}")
	public Boolean checkEnrollmentByUserIdAndCourseId(@PathVariable("uid") int userId,
			@PathVariable("cid") int courseId);

}
