package com.cts.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "enrollments_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int enrollmentId;
	@NotNull(message = "User Id can't be NULL")
	@Min(value = 1, message = "Quiz ID must be greater than 0")
	private int userId;
	@NotNull(message = "Course Id can't be NULL")
	@Min(value = 1, message = "Quiz ID must be greater than 0")
	private int courseId;
	private LocalDateTime enrollmentDate = LocalDateTime.now();

}
