package com.cts.service;

import java.util.List;

import com.cts.dto.QuizSubmissionDTO;
import com.cts.exception.QuizNotFound;
import com.cts.exception.QuizSubmissionNotFound;
import com.cts.model.Quiz;
import com.cts.model.QuizSubmission;

public interface QuizService {
	public abstract String createQuiz(Quiz quiz);

	public abstract Quiz updateQuiz(Quiz quiz) throws QuizNotFound;

	public abstract String deleteQuiz(int quizId) throws QuizNotFound;

	public abstract Quiz getQuizById(int quizId) throws QuizNotFound;

	public abstract QuizSubmissionDTO evaluateQuiz(QuizSubmission quizSubmission) throws QuizNotFound, QuizSubmissionNotFound;

	public abstract List<Quiz> getAllQuizzes();

	public abstract List<QuizSubmission> getAllQuizSubmissionByUserId(int userId) throws QuizSubmissionNotFound;

	public abstract List<Quiz> getQuizByCourseId(int courseId) ;
	
	public abstract String deleteQuizByCourseId(int courseId) ;

	public abstract QuizSubmission getQuizSubmissionByUserIdAndQuizId(int userId, int quizId);

	public abstract String deleteQuizSubmissionByQuizId(int quizId) throws QuizNotFound, QuizSubmissionNotFound;
	
	public abstract List<QuizSubmission> getAllQuizSubmissions();
	
	public abstract String deleteQuizSubmissionByUserId(int userId) throws QuizSubmissionNotFound;

}
