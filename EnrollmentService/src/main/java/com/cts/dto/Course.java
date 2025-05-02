package com.cts.dto;

import java.util.List;

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
	private List<Course> prerequistes;
	private String courseLanguage;
	private int courseDuration;
}
