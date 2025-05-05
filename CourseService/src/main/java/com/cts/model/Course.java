package com.cts.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
	private int courseId;

	private String courseTitle;
	private String courseDescription;
	private String courseCategory;
	private int instructorId;
	private String prerequistes;
	private String courseLanguage;
	private int courseDuration;

}
