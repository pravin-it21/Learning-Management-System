package com.cts.model;

import java.util.Map;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="quiz_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int quizId;

    @Min(value = 1, message = "Course ID must be greater than 0")
    private int courseId;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @Min(value = 1, message = "Total marks must be at least 1")
    private int totalMarks;

    @ElementCollection
    @NotEmpty(message = "Questions list cannot be empty")
    private Map<Integer, String> questions; // Stores questions mapped to their question number

    @ElementCollection
    @NotEmpty(message = "Answer list cannot be empty")
    private Map<Integer, String> correctAnswers; // Stores correct answers mapped to their question number
}
