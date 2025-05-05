package com.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.model.User;
import com.cts.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService service;

	@PostMapping("/register")
	public String createUser(@RequestBody User user) {
		return service.createUser(user);
	}

	@PutMapping("/update")
	public User updateUser(@RequestBody User user) {
		return service.updateUser(user);
	}

	@DeleteMapping("/remove/{uid}")
	public String removeUser(@PathVariable("uid") int userId) {
		return service.removeUser(userId);
	}

	@GetMapping("/fetchById/{uid}")
	public User getById(@PathVariable("uid") int userId) {
		return service.getUserById(userId);
	}
	
	@GetMapping("/checkUserExist/{uid}")
	public Boolean checkUserExist(@PathVariable("uid") int userId) {
		return service.checkUserExist(userId);
	}
	
	
	@GetMapping("/fetchByEmail/{uemail}")
	public User getByEmail(@PathVariable("uemail") String userEmail) {
		return service.getUserByEmail(userEmail);
	}
	
	@GetMapping("/fetchAll")
	public List<User> getAllUsers() {
		return service.getAllUsers();
	}

}
