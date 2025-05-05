package com.cts.model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
	private int courseId;
	private String title;
	private int totalMarks;
	@ElementCollection
	private List<String> questions;
	@ElementCollection
	private List<String> correctAnswers;
}
