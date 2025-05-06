package com.cts.service;

import java.util.List;

import com.cts.exception.UserNotFound;
import com.cts.model.User;

public interface UserService {

	public abstract String createUser(User user);

	public abstract User updateUser(User user);

	public abstract String removeUser(int userId);

	public abstract User getUserById(int userId) throws UserNotFound;

	public abstract List<User> getAllUsers();

	public abstract User getUserByEmail(String email);

	public abstract Boolean checkUserExist(int userId) throws UserNotFound;

}
