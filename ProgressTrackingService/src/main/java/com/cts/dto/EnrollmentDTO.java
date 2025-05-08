package com.cts.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDTO {
	private int enrollmentId;
	private int userId;
	private int courseId;
	private LocalDateTime enrollmentDate;
}
