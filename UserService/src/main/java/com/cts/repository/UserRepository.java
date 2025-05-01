package com.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByUserEmail(String userEmail);

}
