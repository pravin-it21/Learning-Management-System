package com.cts.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.dto.CourseDTO;
import com.cts.dto.QuizDTO;
import com.cts.dto.QuizSubmissionDTO;

@FeignClient(name = "QUIZSERVICE", path = "/quiz")
public interface QuizClient {
	

	@GetMapping("/getSubmissionByUserIdAndQuizId/{uid}/{qid}")
	public abstract QuizSubmissionDTO getQuizSubmissionByUserIdAndQuizId(@PathVariable("uid") int userId,
			@PathVariable("qid") int quizId);

	@GetMapping("/getQuizByCourseId/{cid}")
	public abstract List<QuizDTO> getQuizByCourseId(@PathVariable("cid") int courseId);

}
