package com.cts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.model.Question;
import com.cts.repository.QuestionRepository;

@Service
public class QuestionServiceImpl implements QuestionService {
	@Autowired
	QuestionRepository repository;

	@Override
	public String addQuestion(Question question) {
		repository.save(question);
		return "Question Saved";
	}

	@Override
	public Question updateQuestion(Question question) {

		return repository.save(question);
	}

	@Override
	public String removeQuestion(int questionId) {
		repository.delete(repository.findById(questionId).get());
		return "Question Deleted";
	}

	@Override
	public Question getById(int questionId) {
		return repository.findById(questionId).get();
	}

	@Override
	public List<Question> fetchAll() {
		return repository.findAll();
	}

	@Override
	public List<Question> getQuestionByQuizId(int quizId) {
		return repository.findByQuizId(quizId);
	}

}
