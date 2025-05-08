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

import com.cts.model.Quiz;
import com.cts.model.QuizSubmission;
import com.cts.service.QuizService;

@RestController
@RequestMapping("/quiz")
public class QuizController {

	@Autowired
	QuizService quizService;

	@PostMapping("/create")
	public String createQuiz(@RequestBody Quiz quiz) {
		return quizService.createQuiz(quiz);
	}

	@PutMapping("/update")
	public Quiz updateQuiz(@RequestBody Quiz quiz) {
		return quizService.updateQuiz(quiz);
	}

	@DeleteMapping("/delete/{qid}")
	public String deleteQuiz(@PathVariable("qid") int quizId) {
		return quizService.deleteQuiz(quizId);
	}

	@GetMapping("/getById/{qid}")
	public Quiz getById(@PathVariable("qid") int quizId) {
		return quizService.getQuizById(quizId);
	}
	
	@GetMapping("/getQuizByCourseId/{cid}")
	public List<Quiz> getQuizByCourseId(@PathVariable("cid") int courseId) {
		return quizService.getQuizByCourseId(courseId);
	}
	
	@GetMapping("/getAllQuizSubmissionByUserId/{uid}")
	public List<QuizSubmission> getAllQuizSubmissionByUserId(@PathVariable("uid") int userId) {
		return quizService.getAllQuizSubmissionByUserId(userId);
	}
	
	@GetMapping("/getSubmissionByUserId/{uid}/{qid}")
	public QuizSubmission getQuizSubmissionByUserId(@PathVariable("uid") int userId,@PathVariable("qid") int quizId) {
		return quizService.getQuizSubmissionByUserId(userId,quizId);
	}

	@GetMapping("/fetchAll")
	public List<Quiz> getAllQuizzes() {
		return quizService.getAllQuizzes();
	}

	@PostMapping("/submit")
	public QuizSubmission evaluteQuiz(@RequestBody QuizSubmission quizSubmission) {
		return quizService.evaluateQuiz(quizSubmission);
	}
}
