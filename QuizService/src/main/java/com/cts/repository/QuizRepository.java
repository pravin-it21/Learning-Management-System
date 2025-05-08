package com.cts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.model.Quiz;
import com.cts.model.QuizSubmission;

public interface QuizRepository extends JpaRepository<Quiz, Integer>{

	List<Quiz> findByCourseId(int courseId);


}
