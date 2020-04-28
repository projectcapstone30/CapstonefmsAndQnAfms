package com.fms.domain;

public class AnswersDTO {
	
	
private Long answerId;

private String answerName;

public AnswersDTO() {
	super();
}

public AnswersDTO(Long answerId, String answerName) {
	super();
	this.answerId = answerId;
	this.answerName = answerName;
}

public Long getAnswerId() {
	return answerId;
}

public void setAnswerId(Long answerId) {
	this.answerId = answerId;
}

public String getAnswerName() {
	return answerName;
}

public void setAnswerName(String answerName) {
	this.answerName = answerName;
}


}
