package com.cts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import com.cts.model.User;
import com.cts.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository repository;

	@Override
	public String createUser(User user) {
		user.setUserPassword(encryptPassword(user.getUserPassword()));
		repository.save(user);
		return "User Saved ";
	}

	@Override
	public User updateUser(User user) {
		return repository.save(user);
	}

	@Override
	public String removeUser(int userId) {
		repository.deleteById(userId);
		return "User Deleted";
	}

	@Override
	public User getUserById(int userId) {

		return repository.findById(userId).get();
	}

	@Override
	public User getUserByEmail(String userEmail) {

		return repository.findByUserEmail(userEmail);
	}

	private String encryptPassword(String password) {

		return new BCryptPasswordEncoder().encode(password);
	}

	@Override
	public List<User> getAllUsers() {
		return repository.findAll();
	}

}
