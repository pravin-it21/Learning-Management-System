package com.cts.dto;

import com.cts.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private String name;
	private String email;
	private String password;
	private Role role;
}
