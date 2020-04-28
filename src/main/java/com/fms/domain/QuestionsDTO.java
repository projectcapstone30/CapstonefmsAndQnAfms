package com.fms.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionsDTO {
	
	private Long questionId;
	
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public String getQuestionName() {
		return questionName;
	}
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}
	public List<AnswersDTO> getAnswers() {
		return answers;
	}
	public void setAnswers(List<AnswersDTO> answers) {
		this.answers = answers;
	}
	public FeedbackType getFeedbackType() {
		return feedbackType;
	}
	public void setFeedbackType(FeedbackType feedbackType) {
		this.feedbackType = feedbackType;
	}
	private String questionName;
	
	private List<AnswersDTO> answers;
	private FeedbackType feedbackType;
	
	

}
