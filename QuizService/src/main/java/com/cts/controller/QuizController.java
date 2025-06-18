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

import com.cts.dto.QuizSubmissionDTO;
import com.cts.exception.QuizNotFound;
import com.cts.exception.QuizSubmissionNotFound;
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
	public Quiz updateQuiz(@RequestBody Quiz quiz) throws QuizNotFound {
		return quizService.updateQuiz(quiz);
	}

	@DeleteMapping("/delete/{qid}")
	public String deleteQuiz(@PathVariable("qid") int quizId) throws QuizNotFound {
		return quizService.deleteQuiz(quizId);
	}

	@GetMapping("/getById/{qid}")
	public Quiz getById(@PathVariable("qid") int quizId) throws QuizNotFound {
		return quizService.getQuizById(quizId);
	}
	
	@GetMapping("/getQuizByCourseId/{cid}")
	public List<Quiz> getQuizByCourseId(@PathVariable("cid") int courseId) throws QuizNotFound  {
		return quizService.getQuizByCourseId(courseId);
	}
	
	@DeleteMapping("/deleteQuizByCourseId/{cid}")
	public String deleteQuizByCourseId(@PathVariable("cid") int courseId) {
		return quizService.deleteQuizByCourseId(courseId);
	}
	
	@DeleteMapping("/deleteQuizSubmissionByQuizId/{qid}")
	public String deleteQuizSubmissionByQuizId(@PathVariable("qid") int quizId) throws QuizNotFound, QuizSubmissionNotFound {
		return quizService.deleteQuizSubmissionByQuizId(quizId);
	}
	
	@GetMapping("/getAllQuizSubmissionByUserId/{uid}")
	public List<QuizSubmission> getAllQuizSubmissionByUserId(@PathVariable("uid") int userId) throws QuizSubmissionNotFound {
		return quizService.getAllQuizSubmissionByUserId(userId);
	}
	
	@GetMapping("/getSubmissionByUserIdAndQuizId/{uid}/{qid}")
	public QuizSubmission getQuizSubmissionByUserIdAndQuizId(@PathVariable("uid") int userId,@PathVariable("qid") int quizId) throws QuizSubmissionNotFound, QuizNotFound {
		return quizService.getQuizSubmissionByUserIdAndQuizId(userId,quizId);
	}

	@GetMapping("/fetchAllQuiz")
	public List<Quiz> getAllQuizzes() {
		return quizService.getAllQuizzes();
	}
	
	@GetMapping("/fetchAllQuizSubmission")
	public List<QuizSubmission> getAllQuizSubmissions() {
		return quizService.getAllQuizSubmissions();
	}

	@PostMapping("/submit")
	public QuizSubmission evaluteQuiz(@RequestBody QuizSubmission quizSubmission) throws QuizNotFound, QuizSubmissionNotFound {
		return quizService.evaluateQuiz(quizSubmission);
	}
	
	@DeleteMapping("/deleteQuizSubmissionByUserId/{uid}")
	public String deleteQuizSubmissionByUserId(@PathVariable("uid") int userId) throws QuizSubmissionNotFound  {
		return quizService.deleteQuizSubmissionByUserId(userId);
	}
	
	@GetMapping("/checkSubmission/{uid}/{qid}")
	public Boolean checkSubmission(@PathVariable("uid") int userId,@PathVariable("qid") int quizId){
		return quizService.checkSubmission(userId,quizId);
	}
}
