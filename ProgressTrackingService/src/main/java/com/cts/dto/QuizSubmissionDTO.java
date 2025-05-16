package com.cts.dto;

import java.util.List;
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
	private int userId;
	private Map<Integer, String> responses;
	private int score;
	private boolean passed;
	
}
