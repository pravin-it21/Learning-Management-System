package com.cts.service;

import java.util.List;

import com.cts.model.Question;

public interface QuestionService {
	public abstract String addQuestion(Question question);

	public abstract Question updateQuestion(Question question);

	public abstract String removeQuestion(int questionId);

	public abstract Question getById(int questionId);

	public abstract List<Question> fetchAll();

	public abstract List<Question> getQuestionByQuizId(int quizId);
}
