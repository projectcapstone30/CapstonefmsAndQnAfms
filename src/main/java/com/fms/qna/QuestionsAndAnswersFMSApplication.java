package com.fms.qna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
@ComponentScan(basePackages="com.fms")
public class QuestionsAndAnswersFMSApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestionsAndAnswersFMSApplication.class, args);
	}

}
