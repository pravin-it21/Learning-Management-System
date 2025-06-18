package com.cts;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

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

@SpringBootTest
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
        Quiz quiz = new Quiz(1, 101, "Cloud Computing Quiz", 50, 
                            Map.of(1, "What is AWS?", 2, "Define Kubernetes"), 
                            "{ \"1\": [\"Option A\", \"Option B\"], \"2\": [\"Option X\", \"Option Y\"] }",
                            Map.of(1, "Option A", 2, "Option X"));

        Mockito.when(courseClient.checkCourseExist(quiz.getCourseId())).thenReturn(true);
        Mockito.when(quizRepository.save(quiz)).thenReturn(quiz);

        String result = quizService.createQuiz(quiz);

        assertEquals("Quiz Created", result);
        Mockito.verify(quizRepository, Mockito.times(1)).save(quiz);
    }

    @Test
    void testGetQuizById_Success() throws QuizNotFound {
        Quiz quiz = new Quiz(1, 101, "Cloud Computing Quiz", 50, 
                            Map.of(1, "What is AWS?", 2, "Define Kubernetes"), 
                            "{ \"1\": [\"Option A\", \"Option B\"], \"2\": [\"Option X\", \"Option Y\"] }",
                            Map.of(1, "Option A", 2, "Option X"));

        Mockito.when(quizRepository.findById(1)).thenReturn(Optional.of(quiz));

        Quiz foundQuiz = quizService.getQuizById(1);

        assertEquals("Cloud Computing Quiz", foundQuiz.getTitle());
    }

    @Test
    void testDeleteQuiz_Success() throws QuizNotFound {
        Quiz quiz = new Quiz(1, 101, "Cloud Computing Quiz", 50, 
                            Map.of(1, "What is AWS?", 2, "Define Kubernetes"), 
                            "{ \"1\": [\"Option A\", \"Option B\"], \"2\": [\"Option X\", \"Option Y\"] }",
                            Map.of(1, "Option A", 2, "Option X"));

        Mockito.when(quizRepository.findById(1)).thenReturn(Optional.of(quiz));

        String result = quizService.deleteQuiz(1);

        assertEquals("Quiz Deleted", result);
        Mockito.verify(quizRepository, Mockito.times(1)).delete(quiz);
    }

    @Test
    void testEvaluateQuiz_Success() throws QuizNotFound, QuizSubmissionNotFound {
        int userId = 1;
        int quizId = 101;

        Map<Integer, String> correctAnswers = Map.of(
                1, "Option A",
                2, "Option X"
        );

        Map<Integer, String> userResponses = Map.of(
                1, "Option A",
                2, "Option Y"
        );

        Quiz quiz = new Quiz(quizId, 201, "Java Quiz", 30, correctAnswers, "{ \"1\": [\"Option A\", \"Option B\"], \"2\": [\"Option X\", \"Option Y\"] }", correctAnswers);

        Mockito.when(userClient.checkUserExist(userId)).thenReturn(true);
        Mockito.when(quizRepository.findById(quizId)).thenReturn(Optional.of(quiz));
        Mockito.when(enrollmentClient.checkEnrollmentByUserIdAndCourseId(userId, quiz.getCourseId())).thenReturn(true);
        Mockito.when(submissionRepository.findByUserIdAndQuizId(userId, quizId)).thenReturn(null);

        QuizSubmission submission = new QuizSubmission(500, quizId, userId, userResponses, 10, false, 1, 1, new HashMap<>(), new HashMap<>());

        Mockito.when(submissionRepository.save(Mockito.any())).thenReturn(submission);

        QuizSubmission evaluatedSubmission = quizService.evaluateQuiz(submission);

        assertEquals(10, evaluatedSubmission.getScore());
        assertFalse(evaluatedSubmission.isPassed());
        Mockito.verify(submissionRepository, Mockito.times(1)).save(Mockito.any());
    }
}
