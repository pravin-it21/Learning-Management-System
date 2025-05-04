package com.cts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer>{

	public abstract List<Question> findByQuizId(int quizId);

}
