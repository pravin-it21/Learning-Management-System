package com.cts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizProgressDTO {
	private int quizId;
	private int totalMarks;
	private int score;
	private double progressPercentage;
}
