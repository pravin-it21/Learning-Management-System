package com.cts.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.exception.QuizNotFound;
import com.cts.exception.QuizSubmissionNotFound;
import com.cts.feignclient.CourseClient;
import com.cts.feignclient.EnrollmentClient;
import com.cts.feignclient.UserClient;
import com.cts.model.Quiz;
import com.cts.model.QuizSubmission;
import com.cts.repository.QuizRepository;
import com.cts.repository.QuizSubmissionRepository;

import jakarta.transaction.Transactional;

@Service
public class QuizServiceImpl implements QuizService {
	@Autowired
	QuizRepository quizRepository;
	@Autowired
	QuizSubmissionRepository submissionRepository;

	@Autowired
	UserClient userClient;
	@Autowired
	CourseClient courseClient;

	@Autowired
	EnrollmentClient enrollmentClient;

	@Override
	public String createQuiz(Quiz quiz) {
		courseClient.checkCourseExist(quiz.getCourseId());
		quizRepository.save(quiz);
		return "Quiz Created";
	}

	@Override
	public Quiz updateQuiz(Quiz quiz) throws QuizNotFound {
		Optional<Quiz> optional = quizRepository.findById(quiz.getQuizId());
		if (optional.isPresent())
			return quizRepository.save(quiz);
		else
			throw new QuizNotFound("Quiz Id is Invalid");
	}

	@Override
	@Transactional
	public String deleteQuiz(int quizId) throws QuizNotFound {
		Optional<Quiz> optional = quizRepository.findById(quizId);
		if (optional.isPresent()) {
			quizRepository.delete(quizRepository.findById(quizId).get());
			submissionRepository.deleteByQuizId(quizId);
			return "Quiz Deleted";
		} else
			throw new QuizNotFound("Quiz Id is Invalid");
	}

	@Override
	public Quiz getQuizById(int quizId) throws QuizNotFound {
		Optional<Quiz> optional = quizRepository.findById(quizId);
		if (optional.isPresent()) {
			return quizRepository.findById(quizId).get();
		} else
			throw new QuizNotFound("Quiz Id is Invalid");
	}

	@Override
	public QuizSubmission evaluateQuiz(QuizSubmission quizSubmission) throws QuizNotFound, QuizSubmissionNotFound {
		int userId = quizSubmission.getUserId();
		Boolean responseUser = userClient.checkUserExist(userId);
		QuizSubmission submissionExist = submissionRepository.findByUserIdAndQuizId(userId, quizSubmission.getQuizId());
		if (submissionExist == null) {
			Optional<Quiz> optional = quizRepository.findById(quizSubmission.getQuizId());
			if (optional.isPresent()) {
				Quiz quiz = optional.get();
				int courseId = quiz.getCourseId();
				enrollmentClient.checkEnrollmentByUserIdAndCourseId(userId, courseId);
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
			} else
				throw new QuizNotFound("Quiz Id is Invalid");
		} else
			throw new QuizSubmissionNotFound("Submission Aldready Found");
	}

	@Override
	public List<Quiz> getAllQuizzes() {
		return quizRepository.findAll();
	}

	@Override
	public List<QuizSubmission> getAllQuizSubmissionByUserId(int userId) throws QuizSubmissionNotFound {
		Boolean responseUser = userClient.checkUserExist(userId);
		List<QuizSubmission> list = submissionRepository.findByUserId(userId);
		if (list.isEmpty())
			throw new QuizSubmissionNotFound("No Submission For This User Found");
		return list;
	}

	@Override
	public List<Quiz> getQuizByCourseId(int courseId) {
		courseClient.checkCourseExist(courseId);
		List<Quiz> list = quizRepository.findByCourseId(courseId);
//		if (list.isEmpty())
//			throw new QuizNotFound("Quiz For this Course Not Found");
		return list;
	}

	@Override
	public QuizSubmission getQuizSubmissionByUserIdAndQuizId(int userId, int quizId) {
		userClient.checkUserExist(userId);
		return submissionRepository.findByUserIdAndQuizId(userId, quizId);
//		Optional<Quiz> optional = quizRepository.findById(quizId);
//		if (optional.isPresent()) {
//			QuizSubmission submissions = submissionRepository.findByUserIdAndQuizId(userId, quizId);
//			if (submissions != null) {
//				return submissions;
//			} else {
//				throw new QuizSubmissionNotFound("No Submission For this Quiz Available");
//			}

//		} else
//			throw new QuizNotFound("Quiz Id is Invalid");

	}

	@Override
	@Transactional
	public String deleteQuizByCourseId(int courseId) throws QuizNotFound {
		courseClient.checkCourseExist(courseId);
		List<Quiz> list = quizRepository.findByCourseId(courseId);
		if (list.isEmpty())
			throw new QuizNotFound("Quiz For this Course Not Found");
		quizRepository.deleteByCourseId(courseId);
		return "All the quizzes deleted";
	}

	@Override
	@Transactional
	public String deleteQuizSubmissionByQuizId(int quizId) throws QuizNotFound, QuizSubmissionNotFound {
		Optional<Quiz> optional = quizRepository.findById(quizId);
		if (optional.isPresent()) {
			Optional<QuizSubmission> submissions = submissionRepository.findByQuizId(quizId);
			if (submissions.isPresent()) {
				submissionRepository.deleteByQuizId(quizId);
				return "All Quiz Submission For This Quiz Deleted";
			} else {
				throw new QuizSubmissionNotFound("No Submission For this Quiz Available");
			}

		} else
			throw new QuizNotFound("Quiz Id is Invalid");

	}

	@Override
	public List<QuizSubmission> getAllQuizSubmissions() {
		return submissionRepository.findAll();
	}

	@Override
	@Transactional
	public String deleteQuizSubmissionByUserId(int userId) throws QuizSubmissionNotFound {
		userClient.checkUserExist(userId);
		List<QuizSubmission> response = submissionRepository.findByUserId(userId);
		if (!response.isEmpty()) {
			submissionRepository.deleteByUserId(userId);
			return "Submission For this User Deleted";
		} else
			throw new QuizSubmissionNotFound("No Submission Found For This User");
	}

}
