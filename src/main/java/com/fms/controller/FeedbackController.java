package com.fms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

import com.fms.domain.FeedbackType;
import com.fms.qna.service.FeedbackServiceImpl;

@RestController
public class FeedbackController {
	@Autowired
	FeedbackServiceImpl feedbackServiceImpl;

	@GetMapping("/feedbackType")
	public Flux<FeedbackType> getFeedbackType() {

		return feedbackServiceImpl.getAllFeedbackTypes();
	}
}
