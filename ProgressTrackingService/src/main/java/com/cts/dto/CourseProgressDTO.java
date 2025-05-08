package com.cts.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseProgressDTO {
	private int courseId;
	private String courseTitle;
	private String courseDescription;
	private List<QuizProgressDTO> quizzes;
}
