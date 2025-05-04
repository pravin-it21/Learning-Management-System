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

import com.cts.model.Question;
import com.cts.service.QuestionService;

@RestController
@RequestMapping("/question")
public class QuestionController {
	@Autowired
	QuestionService service;

	@PostMapping("/add")
	public String addQuestion(@RequestBody Question question) {
		return service.addQuestion(question);
	}

	@PutMapping("/update")
	public Question updateQuestion(@RequestBody Question question) {
		return service.updateQuestion(question);
	}

	@DeleteMapping("/remove/{qid}")
	public String removeQuestion(@PathVariable("qid") int questionId) {
		return service.removeQuestion(questionId);
	}

	@GetMapping("/fetchById/{qid}")
	public Question getById(@PathVariable("qid") int questionId) {
		return service.getById(questionId);
	}

	@GetMapping("/fetchAll")
	public List<Question> getAll() {
		return service.fetchAll();
	}

	@GetMapping("/getByQuizId/{qid}")
	public List<Question> getQuestionByQuizId(@PathVariable("qid") int quizId) {
		return service.getQuestionByQuizId(quizId);
	}
}
