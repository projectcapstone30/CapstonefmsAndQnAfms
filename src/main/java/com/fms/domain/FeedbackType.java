package com.fms.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("feed_back")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackType {
	
	@Id
	@Column("feedback_key")
	private Long feedbackId;
	
	@Column("feedback_type")
	private String feedbackType;

	
}
