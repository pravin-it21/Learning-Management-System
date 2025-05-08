package com.cts.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private int userId;
//	private String userName;
//	private String userEmail;
	private List<CourseProgressDTO> courses;
}
