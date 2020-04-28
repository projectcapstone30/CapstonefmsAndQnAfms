package com.fms.qna.repository;

import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;

import com.fms.domain.Answers;

public interface AnswersRepository extends ReactiveCrudRepository<Answers, Long> {
	@Query("Select * from answers as a Inner join questions as q on a.q_id= q.q_id where a.q_id=?")
	Flux<Answers> findAnswersByQuestionId(Long id);

}
