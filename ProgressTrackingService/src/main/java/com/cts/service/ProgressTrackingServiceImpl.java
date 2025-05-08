package com.cts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.dto.CourseDTO;
import com.cts.dto.CourseProgressDTO;
import com.cts.dto.QuizDTO;
import com.cts.dto.QuizProgressDTO;
import com.cts.dto.QuizSubmissionDTO;
import com.cts.dto.UserDTO;
import com.cts.feignclient.EnrollmentClient;
import com.cts.feignclient.QuizClient;
@Service
public class ProgressTrackingServiceImpl implements ProgressTrackingService {
	@Autowired
	EnrollmentClient enrollmentClient;
	
	@Autowired
	QuizClient quizClient;
	@Override
	public List<CourseDTO> getCourseByUserId(int userId) {
		return enrollmentClient.getCoursesByUserId(userId);
	}
	@Override
	public UserDTO getProgressByUserId(int userId) {
		List<CourseDTO> courses = enrollmentClient.getCoursesByUserId(userId);
		List<CourseProgressDTO> courseProgressDTOs = new ArrayList<>();
		for(CourseDTO course:courses) {
			int courseId = course.getCourseId();
			List<QuizDTO> quizzes =  quizClient.getQuizByCourseId(courseId);
			List<QuizProgressDTO> quizProgressDTOs = new ArrayList<>();
			for(QuizDTO quiz:quizzes) {
				QuizSubmissionDTO submissionDTO = quizClient.getQuizSubmissionByUserId(userId,quiz.getQuizId());
				int totalMarks = quiz.getTotalMarks();
				int score = submissionDTO!=null?submissionDTO.getScore():0;
                double progressPercentage = (double) score / totalMarks * 100;
                quizProgressDTOs.add(new QuizProgressDTO(quiz.getQuizId(),totalMarks,score,progressPercentage));				
			}
			courseProgressDTOs.add(new CourseProgressDTO(courseId,course.getCourseTitle(),course.getCourseDescription(), quizProgressDTOs));
		}
		return new UserDTO(userId,courseProgressDTOs);
	}

}
