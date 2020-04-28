package com.fms.repository;

import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.fms.domain.Answers;
import com.fms.domain.Questions;

import reactor.core.publisher.Flux;

public interface QuestionRepository extends ReactiveCrudRepository<Questions, Long>{

	@Query("Select * from questions as q Inner join feedback as f on q.f_key= f.f_key where f.f_key=?")
	Flux<Questions> findQuestionsById(Long id);

}
