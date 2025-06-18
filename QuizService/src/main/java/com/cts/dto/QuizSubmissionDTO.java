package com.cts.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizSubmissionDTO {
	private int submissionId;
	private int quizId;
	private int quizTitle;
	private int userId;
	private Map<Integer, String> responses;
	private int score;
	private boolean passed;
	private int correctAnswersCount;
	private int incorrectAnswersCount;
	private Map<Integer, String> unansweredQuestions; 
	private Map<Integer, String> incorrectQuestions;

}
