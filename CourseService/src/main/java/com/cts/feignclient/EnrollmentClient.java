package com.cts.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ENROLLMENTSERVICE", path = "/enroll")
public interface EnrollmentClient {

	@DeleteMapping("/cancelEnrollmentsByCourseId/{cid}")
	public String cancelEnrollmentsByCourseId(@PathVariable("cid") int courseId);
}
