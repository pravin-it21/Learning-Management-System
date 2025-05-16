package com.cts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
import com.cts.service.QuizServiceImpl;

class QuizServiceImplTest {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuizSubmissionRepository submissionRepository;

    @Mock
    private UserClient userClient;

    @Mock
    private CourseClient courseClient;

    @Mock
    private EnrollmentClient enrollmentClient;

    @InjectMocks
    private QuizServiceImpl quizService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateQuiz_Success() {
        Quiz quiz = new Quiz(1, 101, "Cloud Computing Quiz", 50, new HashMap<>(), new HashMap<>());

        when(courseClient.checkCourseExist(quiz.getCourseId())).thenReturn(true);

        String result = quizService.createQuiz(quiz);

        assertEquals("Quiz Created", result);
        verify(quizRepository, times(1)).save(quiz);
    }

    @Test
    void testGetQuizById_Success() throws QuizNotFound {
        Quiz quiz = new Quiz(1, 101, "Cloud Computing Quiz", 50, new HashMap<>(), new HashMap<>());

        when(quizRepository.findById(1)).thenReturn(Optional.of(quiz));

        Quiz foundQuiz = quizService.getQuizById(1);

        assertEquals("Cloud Computing Quiz", foundQuiz.getTitle());
    }

    @Test
    void testDeleteQuiz_Success() throws QuizNotFound {
        Quiz quiz = new Quiz(1, 101, "Cloud Computing Quiz", 50, new HashMap<>(), new HashMap<>());

        when(quizRepository.findById(1)).thenReturn(Optional.of(quiz));

        String result = quizService.deleteQuiz(1);

        assertEquals("Quiz Deleted", result);
        verify(quizRepository, times(1)).delete(quiz);
    }

    @Test
    void testEvaluateQuiz_Success() throws QuizNotFound, QuizSubmissionNotFound {
        Map<Integer, String> correctAnswers = new HashMap<>();
        Map<Integer, String> questions = new HashMap<>();
        questions.put(1, "What is");
        questions.put(2, "Why");

        correctAnswers.put(1, "Cloud Computing");
        correctAnswers.put(2, "Virtualization");

        Quiz quiz = new Quiz(101, 201, "Cloud Computing Quiz", 30,  questions, correctAnswers);
        QuizSubmission submission = new QuizSubmission(1, 101, 5001, Map.of(1, "Cloud Computing", 2, "Wrong Answer"), 0, false);

        when(quizRepository.findById(101)).thenReturn(Optional.of(quiz));
        when(submissionRepository.findByUserIdAndQuizId(5001, 101)).thenReturn(null);

        QuizSubmissionDTO evaluatedSubmission = quizService.evaluateQuiz(submission);

        assertEquals(10, evaluatedSubmission.getScore()); // One correct answer
        assertFalse(evaluatedSubmission.isPassed());
        assertEquals(1, evaluatedSubmission.getCorrectAnswersCount());
        assertEquals(1, evaluatedSubmission.getIncorrectAnswersCount());
    }
}
