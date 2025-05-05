package com.cts.service;

import java.util.List;

import com.cts.model.Quiz;
import com.cts.model.QuizSubmission;

public interface QuizService {
	public abstract String createQuiz(Quiz quiz);

	public abstract Quiz updateQuiz(Quiz quiz);

	public abstract String deleteQuiz(int quizId);

	public abstract Quiz getQuizById(int quizId);

	public abstract QuizSubmission evaluateQuiz(QuizSubmission quizSubmission);

	public abstract List<Quiz> getAllQuizzes();

}
