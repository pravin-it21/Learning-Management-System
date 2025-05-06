package com.cts.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int courseId;

	@NotBlank(message = "Course Title can't be NULL or BLANK") // it not allow " " space
	private String courseTitle;
	@NotBlank(message = "Course Description can't be NULL or BLANK")
	private String courseDescription;
	@NotBlank(message = "Course Category can't be NULL or BLANK")
	private String courseCategory;
	private int instructorId;
	@NotBlank(message = "Course prerequistes can't be NULL or BLANK")
	private String prerequisites;

}
