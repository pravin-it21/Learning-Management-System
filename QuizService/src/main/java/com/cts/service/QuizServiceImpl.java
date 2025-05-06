package com.cts.service;

import java.util.ArrayList;
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
		if (!courseClient.checkCourseExist(quiz.getCourseId())) {
            return "Error: Course ID " + quiz.getCourseId() + " does not exist!";
        }
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
		List<Boolean> correctness = new ArrayList<>();
		for (int i = 0; i < quizSubmission.getResponses().size(); i++) {
			boolean isCorrect = quizSubmission.getResponses().get(i).equalsIgnoreCase(correctAnswers.get(i));
			correctness.add(isCorrect);
			if (isCorrect) {
				score += 10;
			}
		}
		quizSubmission.setScore(score);
		quizSubmission.setPassed(score >= quiz.getTotalMarks() * 0.6); // Passing criteria: 60%
		submissionRepository.save(quizSubmission);
		return quizSubmission;
	}

	@Override
	public List<Quiz> getAllQuizzes() {
		return quizRepository.findAll();
	}

}
