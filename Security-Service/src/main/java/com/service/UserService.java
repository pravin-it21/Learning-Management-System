package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.entity.UserInfo;
import com.exception.UserNotFound;
import com.repository.UserInfoRepository;

@Service
public class UserService {
	@Autowired
	private UserInfoRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public String addUser(UserInfo userInfo) {
		String name = userInfo.getName();
		UserInfo obj1 = repository.findByName(name).orElse(null);
		System.out.println(obj1);
		if (obj1 == null) {
			userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
			repository.save(userInfo);
			return "Registration Successfully ";
		} else {
			return "This UserName is Already Registered.";
		}
	}

	public String getRoles(String username) {
		UserInfo obj2 = repository.findByName(username).orElse(null);
		if (obj2 != null) {
			return obj2.getRoles();
		}
		return "Not Found";
	}
	
	public UserInfo updateUser(UserInfo user) {
		return repository.save(user);
	}

	public String removeUser(int userId) throws UserNotFound {
		Optional<UserInfo> optional = repository.findById(userId);
		if (optional.isPresent()) {
			repository.deleteById(userId);
			return "User Deleted";
		}
		else
			throw new UserNotFound("User Id is Invalid...");
	}

	public UserInfo getUserById(int userId) throws UserNotFound {
		
		Optional<UserInfo> optional = repository.findById(userId);
		if (optional.isPresent())
			return optional.get();
		else
			throw new UserNotFound("User Id is Invalid...");

	}

	public UserInfo getUserByEmail(String userEmail) {

		return repository.findByEmail(userEmail);
	}

	
	
	public List<UserInfo> getAllUsers() {
		return repository.findAll();
	}

	public Boolean checkUserExist(int userId) throws UserNotFound {
		boolean response = repository.existsById(userId);
		if (response)
			return response;
		else
			throw new UserNotFound("User Id is Invalid...");
	}

}
