package com.cts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.dto.Course;
import com.cts.dto.User;
import com.cts.dto.UserCourseEnrollResponseDTO;
import com.cts.exception.EnrollmentNotFound;
import com.cts.feignclient.CourseClient;
import com.cts.feignclient.UserClient;
import com.cts.model.Enrollment;
import com.cts.repository.EnrollmentRepository;
import com.cts.service.EnrollmentServiceImpl;

@SpringBootTest
class EnrollmentServiceImplTest {

    @Mock
    EnrollmentRepository repository;

    @Mock
    UserClient userClient;

    @Mock
    CourseClient courseClient;

    @InjectMocks
    EnrollmentServiceImpl service;

    @BeforeEach
    void setUp() {
        Mockito.reset(repository, userClient, courseClient);
    }

    @Test
    void saveEnrollmentTest() {
        Enrollment enrollment = new Enrollment(1, 101, 201, LocalDateTime.now());

        Mockito.when(userClient.checkUserExist(enrollment.getUserId())).thenReturn(true);
        Mockito.when(courseClient.checkCourseExist(enrollment.getCourseId())).thenReturn(true);
        Mockito.when(repository.findByUserIdAndCourseId(enrollment.getUserId(), enrollment.getCourseId())).thenReturn(null);
        Mockito.when(repository.save(enrollment)).thenReturn(enrollment);

        String response = service.saveEnrollment(enrollment);
        assertEquals("Enrollment Successfully Saved", response);

        Mockito.verify(repository, Mockito.times(1)).save(enrollment);
    }

    @Test
    void saveEnrollmentAlreadyExistsTest() {
        Enrollment enrollment = new Enrollment(1, 101, 201, LocalDateTime.now());

        Mockito.when(repository.findByUserIdAndCourseId(enrollment.getUserId(), enrollment.getCourseId())).thenReturn(enrollment);

        String response = service.saveEnrollment(enrollment);
        assertEquals("Enrollment Aldready Exist", response);
    }

    @Test
    void updateEnrollmentTest() throws EnrollmentNotFound {
        Enrollment enrollment = new Enrollment(1, 101, 201, LocalDateTime.now());

        Mockito.when(userClient.checkUserExist(enrollment.getUserId())).thenReturn(true);
        Mockito.when(courseClient.checkCourseExist(enrollment.getCourseId())).thenReturn(true);
        Mockito.when(repository.findById(enrollment.getEnrollmentId())).thenReturn(Optional.of(enrollment));
        Mockito.when(repository.save(enrollment)).thenReturn(enrollment);

        Enrollment updatedEnrollment = service.updateEnrollment(enrollment);
        assertEquals(enrollment, updatedEnrollment);

        Mockito.verify(repository, Mockito.times(1)).save(enrollment);
    }

    @Test
    void updateEnrollmentNotFoundTest() {
        Enrollment enrollment = new Enrollment(1, 101, 201, LocalDateTime.now());

        Mockito.when(repository.findById(enrollment.getEnrollmentId())).thenReturn(Optional.empty());

        assertThrows(EnrollmentNotFound.class, () -> service.updateEnrollment(enrollment));
    }

    @Test
    void cancelEnrollmentTest() throws EnrollmentNotFound {
        int enrollmentId = 1;
        Enrollment enrollment = new Enrollment(enrollmentId, 101, 201, LocalDateTime.now());

        Mockito.when(repository.findById(enrollmentId)).thenReturn(Optional.of(enrollment));
        Mockito.doNothing().when(repository).delete(enrollment);

        String response = service.cancelEnrollment(enrollmentId);
        assertEquals("Enrollment Deleted", response);

        Mockito.verify(repository, Mockito.times(1)).delete(enrollment);
    }

    @Test
    void cancelEnrollmentNotFoundTest() {
        int enrollmentId = 2;

        Mockito.when(repository.findById(enrollmentId)).thenReturn(Optional.empty());

        assertThrows(EnrollmentNotFound.class, () -> service.cancelEnrollment(enrollmentId));
    }

    @Test
    void getEnrollmentTest() throws EnrollmentNotFound {
        int enrollmentId = 1;
        Enrollment enrollment = new Enrollment(enrollmentId, 101, 201, LocalDateTime.now());
        User user = new User(101, "John Doe", "john@example.com", "password123");
        Course course = new Course(201, "Java Basics", "Intro to Java", "Programming", 1001, "Basic programming", 30);

        Mockito.when(repository.findById(enrollmentId)).thenReturn(Optional.of(enrollment));
        Mockito.when(userClient.getById(enrollment.getUserId())).thenReturn(user);
        Mockito.when(courseClient.getCourse(enrollment.getCourseId())).thenReturn(course);

        UserCourseEnrollResponseDTO response = service.getEnrollment(enrollmentId);
        assertEquals(user, response.getUser());
        assertEquals(course, response.getCourse());
        assertEquals(enrollment, response.getEnroll());

        Mockito.verify(repository, Mockito.times(1)).findById(enrollmentId);
        Mockito.verify(userClient, Mockito.times(1)).getById(enrollment.getUserId());
        Mockito.verify(courseClient, Mockito.times(1)).getCourse(enrollment.getCourseId());
    }

    @Test
    void getEnrollmentNotFoundTest() {
        int enrollmentId = 2;

        Mockito.when(repository.findById(enrollmentId)).thenReturn(Optional.empty());

        assertThrows(EnrollmentNotFound.class, () -> service.getEnrollment(enrollmentId));
    }

    @Test
    void getAllEnrollmentsTest() {
        List<Enrollment> enrollments = Arrays.asList(
                new Enrollment(1, 101, 201, LocalDateTime.now()),
                new Enrollment(2, 102, 202, LocalDateTime.now()));

        Mockito.when(repository.findAll()).thenReturn(enrollments);

        List<Enrollment> result = service.getAllEnrollments();
        assertEquals(enrollments.size(), result.size());

        Mockito.verify(repository, Mockito.times(1)).findAll();
    }

    @Test
    void checkEnrollmentByUserIdAndCourseIdTest() throws EnrollmentNotFound {
        int userId = 101, courseId = 201;

        Mockito.when(repository.existsByUserIdAndCourseId(userId, courseId)).thenReturn(true);

        assertTrue(service.checkEnrollmentByUserIdAndCourseId(userId, courseId));

        Mockito.verify(repository, Mockito.times(1)).existsByUserIdAndCourseId(userId, courseId);
    }

    @Test
    void checkEnrollmentByUserIdAndCourseIdNotFoundTest() {
        int userId = 101, courseId = 201;

        Mockito.when(repository.existsByUserIdAndCourseId(userId, courseId)).thenReturn(false);

        assertThrows(EnrollmentNotFound.class, () -> service.checkEnrollmentByUserIdAndCourseId(userId, courseId));
    }
}
