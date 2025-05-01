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
@Table(name = "enrollments_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
	@Id
	private int enrollmentId;

	private String courseTitle;
	private String courseDescription;
	private String courseCategory;
	private int instructorId;
	@OneToMany
	private List<Enrollment> prerequistes;
	private String courseLanguage;
	private int courseDuration;

}
