package com.fms.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.fms.domain.AnswerRequestDTO;
import com.fms.domain.Answers;
import com.fms.qna.service.FeedbackServiceImpl;


@RestController
public class AnswersController {
	
	
	
	@Autowired
	FeedbackServiceImpl feedbackServiceImpl;
	
	
	@PostMapping("/pushAnswers")
	public Mono<Answers> saveAnswers(@RequestBody Answers answer){
		return feedbackServiceImpl.saveAnswer(answer);
	}
	
	@PatchMapping("/persistAll/{qId}")
	public Flux<Answers> bulkSaveAnswers(@PathVariable Long qId, @RequestBody AnswerRequestDTO answers) throws InterruptedException, ExecutionException{
		return feedbackServiceImpl.saveAnswers(qId, answers);
	}
	
	@DeleteMapping("/{answerId}")
	public Mono<Void> deleteAnswer(@PathVariable Long answerId){
		return feedbackServiceImpl.deleteAnswerById(answerId);
	}
	
	@GetMapping("/{qId}")
	public Flux<Answers> getAllAnswersById(@PathVariable Long qId)
	{
		return feedbackServiceImpl.getAnswersByQuestionId(qId);
	}
	
}
