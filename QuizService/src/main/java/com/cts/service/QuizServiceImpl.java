package com.cts.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.dto.QuizSubmissionDTO;
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

	Logger log = LoggerFactory.getLogger(QuizServiceImpl.class);

	@Override
	public String createQuiz(Quiz quiz) {
		log.info("In QuizServiceImpl createQuiz method...");

		courseClient.checkCourseExist(quiz.getCourseId());
		quizRepository.save(quiz);
		return "Quiz Created";
	}

	@Override
	public Quiz updateQuiz(Quiz quiz) throws QuizNotFound {
		log.info("In QuizServiceImpl updateQuiz method...");

		Optional<Quiz> optional = quizRepository.findById(quiz.getQuizId());
		if (optional.isPresent())
			return quizRepository.save(quiz);
		else
			throw new QuizNotFound("Quiz Id is Invalid");
	}

	@Override
	@Transactional
	public String deleteQuiz(int quizId) throws QuizNotFound {
		log.info("In QuizServiceImpl deleteQuiz method...");

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
		log.info("In QuizServiceImpl getQuizById method...");

		Optional<Quiz> optional = quizRepository.findById(quizId);
		if (optional.isPresent()) {
			return quizRepository.findById(quizId).get();
		} else
			throw new QuizNotFound("Quiz Id is Invalid");
	}


	@Override
	public QuizSubmissionDTO evaluateQuiz(QuizSubmission quizSubmission) throws QuizNotFound, QuizSubmissionNotFound {
	    log.info("Evaluating quiz submission for User ID: {}", quizSubmission.getUserId());

	    int userId = quizSubmission.getUserId();
	    userClient.checkUserExist(userId);

	    QuizSubmission existingSubmission = submissionRepository.findByUserIdAndQuizId(userId, quizSubmission.getQuizId());
	    if (existingSubmission != null) {
	        throw new QuizSubmissionNotFound("Submission Already Found");
	    }

	    Optional<Quiz> optionalQuiz = quizRepository.findById(quizSubmission.getQuizId());
	    if (optionalQuiz.isEmpty()) {
	        throw new QuizNotFound("Quiz ID is Invalid");
	    }

	    Quiz quiz = optionalQuiz.get();
	    int courseId = quiz.getCourseId();
	    enrollmentClient.checkEnrollmentByUserIdAndCourseId(userId, courseId);

	    int score = 0, correctAnswersCount = 0, incorrectAnswersCount = 0;
	    Map<Integer, String> correctAnswers = quiz.getCorrectAnswers();
	    Map<Integer, String> studentResponses = quizSubmission.getResponses();

	    Map<Integer, String> unansweredQuestions = new HashMap<>();
	    Map<Integer, String> incorrectQuestions = new HashMap<>();

	    // Evaluate user responses based on question numbers
	    for (Map.Entry<Integer, String> entry : correctAnswers.entrySet()) {
	        int questionNumber = entry.getKey();
	        String correctAnswer = entry.getValue();
	        String userAnswer = studentResponses.getOrDefault(questionNumber, null);

	        if (userAnswer == null) {
	            unansweredQuestions.put(questionNumber, correctAnswer);
	            continue;
	        }

	        boolean isCorrect = userAnswer.equalsIgnoreCase(correctAnswer);
	        if (isCorrect) {
	            score += 10;
	            correctAnswersCount++;
	        } else {
	            incorrectQuestions.put(questionNumber, correctAnswer);
	            incorrectAnswersCount++;
	        }

	        log.info("Question {} - User Answer: '{}', Correct Answer: '{}', Is Correct: {}", 
	                 questionNumber, userAnswer, correctAnswer, isCorrect);
	    }

	    quizSubmission.setScore(score);
	    quizSubmission.setPassed(score >= quiz.getTotalMarks() * 0.5);
	    submissionRepository.save(quizSubmission);

	    log.info("Quiz Evaluation Complete for User ID: {} - Score: {}, Correct: {}, Incorrect: {}, Unanswered: {}", 
	             userId, score, correctAnswersCount, incorrectAnswersCount, unansweredQuestions.size());

	    // Returning structured feedback
	    return new QuizSubmissionDTO(
	        quizSubmission.getSubmissionId(),
	        quizSubmission.getQuizId(),
	        quizSubmission.getUserId(),
	        quizSubmission.getResponses(),
	        score,
	        quizSubmission.isPassed(),
	        correctAnswersCount,
	        incorrectAnswersCount,
	        unansweredQuestions,
	        incorrectQuestions
	    );
	}


	@Override
	public List<Quiz> getAllQuizzes() {
		log.info("In QuizServiceImpl getAllQuizzes method...");

		return quizRepository.findAll();
	}

	@Override
	public List<QuizSubmission> getAllQuizSubmissionByUserId(int userId) throws QuizSubmissionNotFound {
		log.info("In QuizServiceImpl getAllQuizSubmissionByUserId method...");

		Boolean responseUser = userClient.checkUserExist(userId);
		List<QuizSubmission> list = submissionRepository.findByUserId(userId);
		if (list.isEmpty())
			throw new QuizSubmissionNotFound("No Submission For This User Found");
		return list;
	}

	@Override
	public List<Quiz> getQuizByCourseId(int courseId) {
		log.info("In QuizServiceImpl getQuizByCourseId method...");

		courseClient.checkCourseExist(courseId);
		List<Quiz> list = quizRepository.findByCourseId(courseId);
//		if (list.isEmpty())
//			throw new QuizNotFound("Quiz For this Course Not Found");
		return list;
	}

	@Override
	public QuizSubmission getQuizSubmissionByUserIdAndQuizId(int userId, int quizId) {
		log.info("In QuizServiceImpl getQuizSubmissionByUserIdAndQuizId method...");

		userClient.checkUserExist(userId);
		return submissionRepository.findByUserIdAndQuizId(userId, quizId);

	}

	@Override
	@Transactional
	public String deleteQuizByCourseId(int courseId)  {
		log.info("In QuizServiceImpl deleteQuizByCourseId method...");

		quizRepository.deleteByCourseId(courseId);
		return "All the quizzes deleted";
	}

	@Override
	@Transactional
	public String deleteQuizSubmissionByQuizId(int quizId) throws QuizNotFound, QuizSubmissionNotFound {
		log.info("In QuizServiceImpl deleteQuizSubmissionByQuizId method...");

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
		log.info("In QuizServiceImpl getAllQuizSubmissions method...");

		return submissionRepository.findAll();
	}

	@Override
	@Transactional
	public String deleteQuizSubmissionByUserId(int userId) throws QuizSubmissionNotFound {
		log.info("In QuizServiceImpl deleteQuizSubmissionByUserId method...");

		userClient.checkUserExist(userId);
		List<QuizSubmission> response = submissionRepository.findByUserId(userId);
		if (!response.isEmpty()) {
			submissionRepository.deleteByUserId(userId);
			return "Submission For this User Deleted";
		} else
			throw new QuizSubmissionNotFound("No Submission Found For This User");
	}

}
