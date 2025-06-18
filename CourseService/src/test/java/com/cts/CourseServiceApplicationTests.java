package com.cts;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.exception.CourseNotFound;
import com.cts.feignclient.EnrollmentClient;
import com.cts.feignclient.QuizClient;
import com.cts.model.Course;
import com.cts.repository.CourseRepository;
import com.cts.service.CourseServiceImpl;

@SpringBootTest
class CourseServiceImplTest {

    @Mock
    CourseRepository repository;

    @Mock
    EnrollmentClient enrollmentClient;

    @Mock
    QuizClient quizClient;

    @InjectMocks
    CourseServiceImpl service;

    @Test
    void createCourseTest() {
        Course course = new Course(1, "Java Basics", "Introduction to Java", "Programming", 101, "Basic coding skills", 30, 
                                   "Java fundamentals content", Arrays.asList("https://youtube.com/java-basics"));

        Mockito.when(repository.findById(course.getCourseId())).thenReturn(Optional.empty());
        Mockito.when(repository.save(course)).thenReturn(course);

        String response = service.createCourse(course);
        assertEquals("Course Saved Successfully", response);

        Mockito.verify(repository).save(course);
        Mockito.verify(repository, Mockito.times(1)).findById(course.getCourseId());
    }

    @Test
    void createCourseAlreadyExistsTest() {
        Course course = new Course(1, "Java Basics", "Introduction to Java", "Programming", 101, "Basic coding skills", 30, 
                                   "Existing course content", Arrays.asList("https://youtube.com/existing-course"));

        Mockito.when(repository.findById(course.getCourseId())).thenReturn(Optional.of(course));

        String response = service.createCourse(course);
        assertEquals("Course Aldready Exist", response);

        Mockito.verify(repository, Mockito.times(1)).findById(course.getCourseId());
        Mockito.verify(repository, Mockito.never()).save(course);
    }

    @Test
    void updateCourseTest() throws CourseNotFound {
        Course course = new Course(1, "Advanced Java", "Deep dive into Java", "Programming", 102, "Java basics required", 45,
                                   "Advanced Java content", Arrays.asList("https://youtube.com/advanced-java"));

        Mockito.when(repository.findById(course.getCourseId())).thenReturn(Optional.of(course));
        Mockito.when(repository.save(course)).thenReturn(course);

        Course updatedCourse = service.updateCourse(course);
        assertEquals(course, updatedCourse);

        Mockito.verify(repository).save(course);
        Mockito.verify(repository, Mockito.times(1)).findById(course.getCourseId());
    }

    @Test
    void updateCourseNotFoundTest() {
        Course course = new Course(2, "Spring Boot", "Building microservices", "Development", 103, "Java & Spring required", 40, 
                                   "Spring Boot course content", Arrays.asList("https://youtube.com/spring-boot"));

        Mockito.when(repository.findById(course.getCourseId())).thenReturn(Optional.empty());

        assertThrows(CourseNotFound.class, () -> service.updateCourse(course));

        Mockito.verify(repository, Mockito.times(1)).findById(course.getCourseId());
        Mockito.verify(repository, Mockito.never()).save(course);
    }

    @Test
    void deleteCourseTest() throws CourseNotFound {
        int courseId = 1;
        Course course = new Course(courseId, "React Basics", "Frontend development", "Web", 104, "HTML & CSS required", 35, 
                                   "React fundamentals", Arrays.asList("https://youtube.com/react-basics"));

        Mockito.when(repository.findById(courseId)).thenReturn(Optional.of(course));
        Mockito.doNothing().when(repository).deleteById(courseId);

        String response = service.deleteCourse(courseId);
        assertEquals("Course Deleted", response);

        Mockito.verify(repository).deleteById(courseId);
        Mockito.verify(enrollmentClient).cancelEnrollmentsByCourseId(courseId);
        Mockito.verify(quizClient).deleteQuizByCourseId(courseId);
    }

    @Test
    void deleteCourseNotFoundTest() {
        int courseId = 2;

        Mockito.when(repository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(CourseNotFound.class, () -> service.deleteCourse(courseId));

        Mockito.verify(repository, Mockito.times(1)).findById(courseId);
        Mockito.verify(repository, Mockito.never()).deleteById(courseId);
        Mockito.verify(enrollmentClient, Mockito.never()).cancelEnrollmentsByCourseId(courseId);
        Mockito.verify(quizClient, Mockito.never()).deleteQuizByCourseId(courseId);
    }

    @Test
    void getCourseTest() throws CourseNotFound {
        int courseId = 1;
        Course course = new Course(courseId, "AWS Fundamentals", "Cloud computing basics", "Cloud", 105, "Basic networking", 50, 
                                   "AWS cloud essentials", Arrays.asList("https://youtube.com/aws-fundamentals"));

        Mockito.when(repository.findById(courseId)).thenReturn(Optional.of(course));

        Course foundCourse = service.getCourse(courseId);
        assertEquals(course, foundCourse);

        Mockito.verify(repository, Mockito.times(1)).findById(courseId);
    }

    @Test
    void getCourseNotFoundTest() {
        int courseId = 3;

        Mockito.when(repository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(CourseNotFound.class, () -> service.getCourse(courseId));
    }

    @Test
    void getAllCoursesTest() {
        List<Course> courses = Arrays.asList(
                new Course(1, "Python Basics", "Intro to Python", "Programming", 106, "No prerequisites", 40, 
                           "Python introduction", Arrays.asList("https://youtube.com/python-basics")),
                new Course(2, "Angular Advanced", "Frontend framework deep dive", "Web", 107, "Basic HTML required", 45, 
                           "Angular fundamentals", Arrays.asList("https://youtube.com/angular-advanced")));

        Mockito.when(repository.findAll()).thenReturn(courses);

        List<Course> allCourses = service.getAllCourses();
        assertEquals(2, allCourses.size());
        assertEquals(courses, allCourses);

        Mockito.verify(repository, Mockito.times(1)).findAll();
    }

    @Test
    void checkCourseExistTest() throws CourseNotFound {
        int courseId = 1;
        Mockito.when(repository.existsById(courseId)).thenReturn(true);

        assertTrue(service.checkCourseExist(courseId));

        Mockito.verify(repository, Mockito.times(1)).existsById(courseId);
    }

    @Test
    void checkCourseDoesNotExistTest() {
        int courseId = 4;

        Mockito.when(repository.existsById(courseId)).thenReturn(false);

        assertThrows(CourseNotFound.class, () -> service.checkCourseExist(courseId));
    }
}
