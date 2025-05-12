package com.cts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
    QuizRepository quizRepository;

    @Mock
    QuizSubmissionRepository quizSubmissionRepository;

    @Mock
    CourseClient courseClient;

    @Mock
    EnrollmentClient enrollmentClient;

    @Mock
    UserClient userClient;

    @InjectMocks
    QuizServiceImpl service;

    @BeforeEach
    void setUp() {
        Mockito.reset(quizRepository, quizSubmissionRepository, courseClient, enrollmentClient, userClient);
    }

    @Test
    void createQuizTest() {
        Quiz quiz = new Quiz(1, 101, "Java Basics Quiz", 50, Arrays.asList("Q1", "Q2"), Arrays.asList("A", "B"));

        Mockito.doNothing().when(courseClient).checkCourseExist(quiz.getCourseId());
        Mockito.when(quizRepository.save(quiz)).thenReturn(quiz);

        String response = service.createQuiz(quiz);
        assertEquals("Quiz Created", response);

        Mockito.verify(courseClient, Mockito.times(1)).checkCourseExist(quiz.getCourseId());
        Mockito.verify(quizRepository, Mockito.times(1)).save(quiz);
    }

    @Test
    void updateQuizTest() throws QuizNotFound {
        Quiz quiz = new Quiz(1, 101, "Updated Java Quiz", 60, Arrays.asList("Q1", "Q2"), Arrays.asList("A", "B"));

        Mockito.when(quizRepository.findById(quiz.getQuizId())).thenReturn(Optional.of(quiz));
        Mockito.when(quizRepository.save(quiz)).thenReturn(quiz);

        Quiz updatedQuiz = service.updateQuiz(quiz);
        assertEquals(quiz, updatedQuiz);

        Mockito.verify(quizRepository, Mockito.times(1)).save(quiz);
    }

    @Test
    void updateQuizNotFoundTest() {
        Quiz quiz = new Quiz(2, 102, "Non-Existent Quiz", 50, Arrays.asList("Q1"), Arrays.asList("A"));

        Mockito.when(quizRepository.findById(quiz.getQuizId())).thenReturn(Optional.empty());

        assertThrows(QuizNotFound.class, () -> service.updateQuiz(quiz));
    }

    @Test
    void deleteQuizTest() throws QuizNotFound {
        Quiz quiz = new Quiz(1, 101, "Java Quiz", 50, Arrays.asList("Q1", "Q2"), Arrays.asList("A", "B"));

        Mockito.when(quizRepository.findById(quiz.getQuizId())).thenReturn(Optional.of(quiz));
        Mockito.doNothing().when(quizRepository).delete(quiz);
        Mockito.doNothing().when(quizSubmissionRepository).deleteByQuizId(quiz.getQuizId());

        String response = service.deleteQuiz(quiz.getQuizId());
        assertEquals("Quiz Deleted", response);

        Mockito.verify(quizRepository, Mockito.times(1)).delete(quiz);
        Mockito.verify(quizSubmissionRepository, Mockito.times(1)).deleteByQuizId(quiz.getQuizId());
    }

    @Test
    void deleteQuizNotFoundTest() {
        int quizId = 999;

        Mockito.when(quizRepository.findById(quizId)).thenReturn(Optional.empty());

        assertThrows(QuizNotFound.class, () -> service.deleteQuiz(quizId));
    }

    @Test
    void evaluateQuizTest() throws QuizNotFound, QuizSubmissionNotFound {
        Quiz quiz = new Quiz(1, 101, "Java Basics Quiz", 50, Arrays.asList("Q1", "Q2"), Arrays.asList("A", "B"));
        QuizSubmission submission = new QuizSubmission(1, quiz.getQuizId(), 201, Arrays.asList("A", "B"), 0, false);

        Mockito.when(userClient.checkUserExist(submission.getUserId())).thenReturn(true);
        Mockito.when(enrollmentClient.checkEnrollmentByUserIdAndCourseId(submission.getUserId(), quiz.getCourseId())).thenReturn(true);
        Mockito.when(quizRepository.findById(submission.getQuizId())).thenReturn(Optional.of(quiz));
        Mockito.when(quizSubmissionRepository.findByUserIdAndQuizId(submission.getUserId(), submission.getQuizId())).thenReturn(null);
        Mockito.when(quizSubmissionRepository.save(submission)).thenReturn(submission);

        QuizSubmission evaluatedSubmission = service.evaluateQuiz(submission);
        assertEquals(50, evaluatedSubmission.getScore());
        assertTrue(evaluatedSubmission.isPassed());

        Mockito.verify(quizSubmissionRepository, Mockito.times(1)).save(submission);
    }

    @Test
    void evaluateQuizSubmissionAlreadyExistsTest() {
        QuizSubmission submission = new QuizSubmission(1, 101, 201, Arrays.asList("A", "B"), 50, true);

        Mockito.when(quizSubmissionRepository.findByUserIdAndQuizId(submission.getUserId(), submission.getQuizId())).thenReturn(submission);

        assertThrows(QuizSubmissionNotFound.class, () -> service.evaluateQuiz(submission));
    }

    @Test
    void getQuizByIdTest() throws QuizNotFound {
        Quiz quiz = new Quiz(1, 101, "Spring Boot Quiz", 70, Arrays.asList("Q1", "Q2"), Arrays.asList("A", "B"));

        Mockito.when(quizRepository.findById(quiz.getQuizId())).thenReturn(Optional.of(quiz));

        Quiz retrievedQuiz = service.getQuizById(quiz.getQuizId());
        assertEquals(quiz, retrievedQuiz);

        Mockito.verify(quizRepository, Mockito.times(1)).findById(quiz.getQuizId());
    }

    @Test
    void getQuizByIdNotFoundTest() {
        int quizId = 999;

        Mockito.when(quizRepository.findById(quizId)).thenReturn(Optional.empty());

        assertThrows(QuizNotFound.class, () -> service.getQuizById(quizId));
    }
}
