package com.cts.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.dto.AdminUserProgressDTO; // Import the new DTO
import com.cts.dto.CourseDTO;
import com.cts.dto.CourseProgressDTO;
import com.cts.dto.QuizDTO;
import com.cts.dto.QuizProgressDTO;
import com.cts.dto.QuizSubmissionDTO;
import com.cts.dto.UserDTO;
import com.cts.dto.UserInfo;
import com.cts.feignclient.EnrollmentClient;
import com.cts.feignclient.QuizClient;
import com.cts.feignclient.UserClient;

@Service
public class ProgressTrackingServiceImpl implements ProgressTrackingService {
    @Autowired
    EnrollmentClient enrollmentClient;

    @Autowired
    UserClient userClient;

    @Autowired
    QuizClient quizClient;

    Logger log = LoggerFactory.getLogger(ProgressTrackingServiceImpl.class);


    // Used To Get Courses By User Id
    @Override
    public List<CourseDTO> getCourseByUserId(int userId) {
        log.info("In ProgressTrackingServiceImpl getCourseByUserId method...");
        return enrollmentClient.getCoursesByUserId(userId);
    }

    // Used To Get Progress For A Particular User
    @Override
    public UserDTO getProgressByUserId(int userId) {
        log.info("Fetching progress for User ID: {}", userId);

        List<CourseDTO> courses = enrollmentClient.getCoursesByUserId(userId);
        List<CourseProgressDTO> courseProgressDTOs = new ArrayList<>();

        for (CourseDTO course : courses) {
            int courseId = course.getCourseId();
            List<QuizDTO> quizzes = quizClient.getQuizByCourseId(courseId);
            List<QuizProgressDTO> quizProgressDTOs = new ArrayList<>();

            int completedQuizzes = 0;

            for (QuizDTO quiz : quizzes) {
                QuizSubmissionDTO submissionDTO = quizClient.getQuizSubmissionByUserIdAndQuizId(userId, quiz.getQuizId());
                int totalMarks = quiz.getTotalMarks();
                int score = (submissionDTO != null) ? submissionDTO.getScore() : 0;
                double progressPercentage = (double) score / totalMarks * 100;

                if (submissionDTO != null && submissionDTO.isPassed()) {
                    completedQuizzes++; // Count completed quizzes
                }

                quizProgressDTOs.add(new QuizProgressDTO(quiz.getQuizId(),quiz.getTitle(), totalMarks, score, progressPercentage));
            }

            // Calculate course completion percentage
            double courseCompletionPercentage = (!quizzes.isEmpty())
                    ? ((double) completedQuizzes / quizzes.size()) * 100
                    : 0;

            courseProgressDTOs.add(new CourseProgressDTO(
                    courseId, course.getCourseTitle(), course.getCourseDescription(), quizProgressDTOs, courseCompletionPercentage));
        }

        return new UserDTO(userId, courseProgressDTOs);
    }


    @Override
    public List<UserDTO> getAllProgress() {
        log.info("Fetching overall progress for all users with user details...");
        List<UserDTO> allProgress = new ArrayList<>();
        List<UserInfo> allUsers = userClient.getAllUsers();

        for (UserInfo user : allUsers) {
            UserDTO userProgress = getProgressByUserId(user.getId());
            // You can directly return a list of UserDTO if you only need progress.
            // If you need UserInfo along with progress, create and use AdminUserProgressDTO as shown below.
            // AdminUserProgressDTO adminUserProgress = new AdminUserProgressDTO(user, userProgress);
            // allProgress.add(adminUserProgress);
            allProgress.add(userProgress); // Returning UserDTO for backward compatibility
        }

        log.info("Successfully fetched overall progress for {} users.", allUsers.size());
        return allProgress;
    }

    // Modified getAllProgress to include UserInfo
    @Override
    public List<AdminUserProgressDTO> getAllProgressWithUserDetails() {
        log.info("Fetching overall progress for all users with user details...");
        List<AdminUserProgressDTO> allProgressWithDetails = new ArrayList<>();
        List<UserInfo> allUsers = userClient.getAllUsers();

        for (UserInfo user : allUsers) {
            UserDTO userProgress = getProgressByUserId(user.getId());
            AdminUserProgressDTO adminUserProgress = new AdminUserProgressDTO(user, userProgress);
            allProgressWithDetails.add(adminUserProgress);
        }

        log.info("Successfully fetched overall progress with details for {} users.", allUsers.size());
        return allProgressWithDetails;
    }
}