package com.cts.model;

import java.util.Map;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="quiz_submissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int submissionId;

    @Min(value = 1, message = "Quiz ID must be greater than 0")
    private int quizId;

    @Min(value = 1, message = "User ID must be greater than 0")
    private int userId;

    @ElementCollection
    private Map<Integer, String> responses; // Stores user-submitted answers mapped to question number

    @Min(value = 0, message = "Score cannot be negative")
    private int score;
    private boolean passed;
    private int correctAnswersCount;
    private int incorrectAnswersCount;
    @ElementCollection
    private Map<Integer, String> unansweredQuestions; 
    @ElementCollection
    private Map<Integer, String> incorrectQuestions;
}


;
