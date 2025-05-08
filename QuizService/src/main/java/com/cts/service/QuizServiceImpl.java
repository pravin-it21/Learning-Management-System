package com.cts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.feignclient.CourseClient;
import com.cts.model.Quiz;
import com.cts.model.QuizSubmission;
import com.cts.repository.QuizRepository;
import com.cts.repository.QuizSubmissionRepository;

@Service
public class QuizServiceImpl implements QuizService {
	@Autowired
	QuizRepository quizRepository;
	@Autowired
	QuizSubmissionRepository submissionRepository;
	
	@Autowired
	CourseClient courseClient;

	@Override
	public String createQuiz(Quiz quiz) {
		courseClient.checkCourseExist(quiz.getCourseId());
		quizRepository.save(quiz);
		return "Quiz Created";
	}

	@Override
	public Quiz updateQuiz(Quiz quiz) {
		return quizRepository.save(quiz);
	}

	@Override
	public String deleteQuiz(int quizId) {
		quizRepository.delete(quizRepository.findById(quizId).get());
		return "Quiz Deleted";
	}

	@Override
	public Quiz getQuizById(int quizId) {
		return quizRepository.findById(quizId).get();
	}

	@Override
	public QuizSubmission evaluateQuiz(QuizSubmission quizSubmission) {
		Quiz quiz = quizRepository.findById(quizSubmission.getQuizId()).get();
		int score = 0;
		List<String> correctAnswers = quiz.getCorrectAnswers();
		for (int i = 0; i < quizSubmission.getResponses().size(); i++) {
			boolean isCorrect = quizSubmission.getResponses().get(i).equalsIgnoreCase(correctAnswers.get(i));
	
			if (isCorrect) {
				score += 10;
			}
		}
		quizSubmission.setScore(score);
		quizSubmission.setPassed(score >= quiz.getTotalMarks() * 0.5); // Passing criteria: 60%
		submissionRepository.save(quizSubmission);
		return quizSubmission;
	}

	@Override
	public List<Quiz> getAllQuizzes() {
		return quizRepository.findAll();
	}

	@Override
	public List<QuizSubmission> getAllQuizSubmissionByUserId(int userId) {
		return submissionRepository.findByUserId(userId);
	}

	@Override
	public List<Quiz> getQuizByCourseId(int courseId) {
		return quizRepository.findByCourseId(courseId);
	}

	@Override
	public QuizSubmission getQuizSubmissionByUserId(int userId, int quizId) {
		return submissionRepository.findByUserIdAndQuizId(userId,quizId);
	}

}
