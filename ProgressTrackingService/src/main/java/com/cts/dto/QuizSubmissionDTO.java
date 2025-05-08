package com.cts.dto;

import java.util.List;

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
	private List<String> responses;
	private int score;
	private boolean passed;
}
