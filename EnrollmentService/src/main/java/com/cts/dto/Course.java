package com.cts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
	private int courseId;

	private String courseTitle;
	private String courseDescription;
	private String courseCategory;
	private int instructorId;
	private String prerequisites;
	private int courseDuration;

}
