package com.cts;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cts.dto.Course;
import com.cts.dto.User;
import com.cts.dto.UserCourseEnrollResponseDTO;
import com.cts.exception.EnrollmentNotFound;
import com.cts.feignclient.CourseClient;
import com.cts.feignclient.UserClient;
import com.cts.model.Enrollment;
import com.cts.repository.EnrollmentRepository;
import com.cts.service.EnrollmentServiceImpl;

class EnrollmentServiceImplTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private UserClient userClient;

    @Mock
    private CourseClient courseClient;

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveEnrollment_Success() {
        Enrollment enrollment = new Enrollment(1, 101, 201,  LocalDateTime.now());

        when(userClient.checkUserExist(101)).thenReturn(true);
        when(courseClient.checkCourseExist(201)).thenReturn(true);
        when(enrollmentRepository.findByUserIdAndCourseId(101, 201)).thenReturn(null);

        String result = enrollmentService.saveEnrollment(enrollment);

        assertEquals("Enrollment Successfully Saved", result);
        verify(enrollmentRepository, times(1)).save(enrollment);
    }

    @Test
    void testSaveEnrollment_AlreadyExists() {
        Enrollment enrollment = new Enrollment(1, 101, 201,  LocalDateTime.now());

        when(userClient.checkUserExist(101)).thenReturn(true);
        when(courseClient.checkCourseExist(201)).thenReturn(true);
        when(enrollmentRepository.findByUserIdAndCourseId(101, 201)).thenReturn(enrollment);

        String result = enrollmentService.saveEnrollment(enrollment);

        assertEquals("Enrollment Aldready Exist", result);
        verify(enrollmentRepository, never()).save(enrollment);
    }

    @Test
    void testGetEnrollment_Success() throws EnrollmentNotFound {
        Enrollment enrollment = new Enrollment(1, 101, 201,  LocalDateTime.now());
        User user = new User(101, "John Doe", "john@example.com");
        Course course = new Course(201, "Cloud Computing", "Learn cloud technologies", "Cloud Technology", 5, "Basic IT knowledge", 20);

        when(enrollmentRepository.findById(1)).thenReturn(Optional.of(enrollment));
        when(userClient.getById(101)).thenReturn(user);
        when(courseClient.getCourse(201)).thenReturn(course);

        UserCourseEnrollResponseDTO response = enrollmentService.getEnrollment(1);

        assertEquals(user, response.getUser());
        assertEquals(course, response.getCourse());
        assertEquals(enrollment, response.getEnroll());
    }

    @Test
    void testGetEnrollment_NotFound() {
        when(enrollmentRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EnrollmentNotFound.class, () -> enrollmentService.getEnrollment(1));
    }

    @Test
    void testCancelEnrollment_Success() throws EnrollmentNotFound {
        Enrollment enrollment = new Enrollment(1, 101, 201,  LocalDateTime.now());
        when(enrollmentRepository.findById(1)).thenReturn(Optional.of(enrollment));

        String result = enrollmentService.cancelEnrollment(1);

        assertEquals("Enrollment Deleted", result);
        verify(enrollmentRepository, times(1)).delete(enrollment);
    }

    @Test
    void testCancelEnrollment_NotFound() {
        when(enrollmentRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EnrollmentNotFound.class, () -> enrollmentService.cancelEnrollment(1));
    }
}
