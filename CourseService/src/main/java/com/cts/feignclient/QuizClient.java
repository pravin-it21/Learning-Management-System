package com.cts.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "QUIZSERVICE", path = "/quiz")
public interface QuizClient {

	@DeleteMapping("/deleteQuizByCourseId/{cid}")
	public String deleteQuizByCourseId(@PathVariable("cid") int courseId);
}
