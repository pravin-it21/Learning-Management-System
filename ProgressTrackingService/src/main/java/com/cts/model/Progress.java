package com.cts.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "progress_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Progress {
//	userId->courseid->quizzes->total - marksscore(10),progress
	@Id
	private int id;

}
