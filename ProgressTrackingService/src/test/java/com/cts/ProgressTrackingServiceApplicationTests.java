package com.cts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.dto.CourseDTO;
import com.cts.dto.QuizDTO;
import com.cts.dto.QuizSubmissionDTO;
import com.cts.dto.UserDTO;
import com.cts.feignclient.EnrollmentClient;
import com.cts.feignclient.QuizClient;
import com.cts.service.ProgressTrackingServiceImpl;

@SpringBootTest
class ProgressTrackingServiceApplicationTests {

    @Mock
    EnrollmentClient enrollmentClient;

    @Mock
    QuizClient quizClient;

    @InjectMocks
    ProgressTrackingServiceImpl service;

    @BeforeEach
    void setUp() {
        Mockito.reset(enrollmentClient, quizClient);
    }

    @Test
    void getCourseByUserIdTest() {
        int userId = 101;
        List<CourseDTO> courses = Arrays.asList(
                new CourseDTO(1, "Java Basics", "Intro to Java", "Programming", 1001, "Basic programming", 30),
                new CourseDTO(2, "Spring Boot", "Microservices", "Development", 1002, "Java basics", 45));

        Mockito.when(enrollmentClient.getCoursesByUserId(userId)).thenReturn(courses);

        List<CourseDTO> result = service.getCourseByUserId(userId);
        assertEquals(2, result.size());
        assertEquals(courses, result);

        Mockito.verify(enrollmentClient, Mockito.times(1)).getCoursesByUserId(userId);
    }

    @Test
    void getProgressByUserIdTest() {
        int userId = 101;
        List<CourseDTO> courses = Arrays.asList(
                new CourseDTO(1, "Java Basics", "Intro to Java", "Programming", 1001, "Basic programming", 30),
                new CourseDTO(2, "Spring Boot", "Microservices", "Development", 1002, "Java basics", 45));

        List<QuizDTO> quizzesForCourse1 = Arrays.asList(
                new QuizDTO(101, 1, "Java Quiz 1", 50),
                new QuizDTO(102, 1, "Java Quiz 2", 60));

        List<QuizDTO> quizzesForCourse2 = Arrays.asList(
                new QuizDTO(201, 2, "Spring Boot Quiz 1", 70));

        QuizSubmissionDTO submission1 = new QuizSubmissionDTO(1, 101, userId, Arrays.asList("A", "B", "C"), 40, true);
        QuizSubmissionDTO submission2 = new QuizSubmissionDTO(2, 102, userId, Arrays.asList("D", "E"), 50, true);
        QuizSubmissionDTO submission3 = new QuizSubmissionDTO(3, 201, userId, Arrays.asList("F", "G"), 60, true);

        Mockito.when(enrollmentClient.getCoursesByUserId(userId)).thenReturn(courses);
        Mockito.when(quizClient.getQuizByCourseId(1)).thenReturn(quizzesForCourse1);
        Mockito.when(quizClient.getQuizByCourseId(2)).thenReturn(quizzesForCourse2);
        Mockito.when(quizClient.getQuizSubmissionByUserIdAndQuizId(userId, 101)).thenReturn(submission1);
        Mockito.when(quizClient.getQuizSubmissionByUserIdAndQuizId(userId, 102)).thenReturn(submission2);
        Mockito.when(quizClient.getQuizSubmissionByUserIdAndQuizId(userId, 201)).thenReturn(submission3);

        UserDTO userProgress = service.getProgressByUserId(userId);
        assertEquals(userId, userProgress.getUserId());
        assertEquals(2, userProgress.getCourses().size());

        Mockito.verify(enrollmentClient, Mockito.times(1)).getCoursesByUserId(userId);
        Mockito.verify(quizClient, Mockito.times(1)).getQuizByCourseId(1);
        Mockito.verify(quizClient, Mockito.times(1)).getQuizByCourseId(2);
        Mockito.verify(quizClient, Mockito.times(1)).getQuizSubmissionByUserIdAndQuizId(userId, 101);
        Mockito.verify(quizClient, Mockito.times(1)).getQuizSubmissionByUserIdAndQuizId(userId, 102);
        Mockito.verify(quizClient, Mockito.times(1)).getQuizSubmissionByUserIdAndQuizId(userId, 201);
    }

    @Test
    void getProgressByUserIdNoQuizzesTest() {
        int userId = 102;
        List<CourseDTO> courses = Arrays.asList(
                new CourseDTO(3, "AWS Fundamentals", "Cloud computing basics", "Cloud", 1003, "Networking basics", 50));

        Mockito.when(enrollmentClient.getCoursesByUserId(userId)).thenReturn(courses);
        Mockito.when(quizClient.getQuizByCourseId(3)).thenReturn(Arrays.asList());

        UserDTO userProgress = service.getProgressByUserId(userId);
        assertEquals(userId, userProgress.getUserId());
        assertEquals(1, userProgress.getCourses().size());
        assertEquals(0, userProgress.getCourses().get(0).getQuizzes().size());

        Mockito.verify(enrollmentClient, Mockito.times(1)).getCoursesByUserId(userId);
        Mockito.verify(quizClient, Mockito.times(1)).getQuizByCourseId(3);
    }
}
