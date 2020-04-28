package com.fms.qna.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.domain.AnswerRequestDTO;
import com.fms.domain.Answers;
import com.fms.domain.AnswersDTO;
import com.fms.domain.FeedbackType;
import com.fms.domain.Questions;
import com.fms.domain.QuestionsDTO;
import com.fms.qna.repository.AnswersRepository;
import com.fms.qna.repository.FeedbackRepository;
import com.fms.repository.QuestionRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	QuestionRepository questionRepository;
	@Autowired
	AnswersRepository answersRepository;

	@Autowired
	FeedbackRepository feedbackRepository;

	@Override
	public Flux<QuestionsDTO> getAllQuestions()
			throws InterruptedException, ExecutionException {

	        List<Questions> questionlist=questionRepository.findAll().collectList().toFuture().get();

		List<QuestionsDTO> questionsDTOs = questionlist
				.stream()
				.map(question -> {
					QuestionsDTO questionDto = new QuestionsDTO();
					questionDto.setQuestionId(question.getQuestionId());
					questionDto.setQuestionName(question.getQuestionName());
					Flux<Answers> answers = answersRepository
							.findAnswersByQuestionId(question.getQuestionId());

					

					List<AnswersDTO> answersDto;
					try {
						answersDto = answers.collectList().toFuture().get()
								.stream().map(answer -> {
									AnswersDTO answerDto = new AnswersDTO();
									answerDto.setAnswerId(answer.getAnswerId());
									answerDto.setAnswerName(answer.getAnswerName());
									return answerDto;
								}).collect(Collectors.toList());
						questionDto.setAnswers(answersDto);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					try {
						questionDto.setFeedbackType(feedbackRepository
								.findById(question.getFeedbackId()).toFuture().get());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return questionDto;
				}).collect(Collectors.toList());
	    

		Flux<QuestionsDTO> daoFlux = Mono.just(questionsDTOs).flatMapMany(Flux::fromIterable);
daoFlux.log();
		return daoFlux;
	}

	@Override
	public Flux<Questions> getAllQuestionsByFeedbackType(Long id) {

		Flux<Questions> questions_feedbackType = questionRepository.findQuestionsById(id);
		System.out.println(questions_feedbackType);
		return questions_feedbackType.log();
	}

	@Override
	public Mono<Questions> saveQuestion(Questions question) {

		Mono<Questions> question1 = questionRepository.save(question);
		System.out.println(question1);
		return question1.log();
	}

	@Override
	public Mono<Answers> saveAnswer(Answers answer) {
		Mono<Answers> answer1 = answersRepository.save(answer);
		
		return answer1.log();
	}

	@Override
	public Mono<Void> deleteAnswerById(Long answerId) {

		return answersRepository.deleteById(answerId);
	}

	@Override
	public Flux<Answers> getAnswersByQuestionId(Long questionId) {
		Flux<Answers> answers = answersRepository.findAnswersByQuestionId(questionId);
		return answers;
	}

	@Override
	public Mono<Void> deleteQuestionById(Long questionId) {

		return questionRepository.deleteById(questionId);
	}

	@Override
	public Mono<QuestionsDTO> getQuestionByQuestionId(Long questionId)
			throws InterruptedException, ExecutionException {

		Mono<Questions> questions = questionRepository.findById(questionId);
		Questions question = questions.toFuture().get();

		QuestionsDTO questionsDTO = new QuestionsDTO();
		questionsDTO.setQuestionId(question.getQuestionId());
		questionsDTO.setQuestionName(question.getQuestionName());
		Flux<Answers> answers = answersRepository.findAnswersByQuestionId(questionId);
		List<Answers> answersList = answers.collectList().toFuture().get();
		
		List<AnswersDTO> answersDto = answersList.stream().map(answer -> {
			AnswersDTO answerDto = new AnswersDTO();
			answerDto.setAnswerId(answer.getAnswerId());
			answerDto.setAnswerName(answer.getAnswerName());
			return answerDto;
		}).collect(Collectors.toList());
		questionsDTO.setAnswers(answersDto);
		questionsDTO.setFeedbackType(feedbackRepository
				.findById(question.getFeedbackId()).toFuture().get());

		return Mono.just(questionsDTO);
	}

	

	@Override
	public Flux<Answers> saveAnswers(Long questionId, AnswerRequestDTO answers) throws InterruptedException, ExecutionException {
		System.out.println(answers);
		Questions question=questionRepository.findById(questionId).toFuture().get();
		System.out.println(question);
		Mono<Questions> finalQ=questionRepository.findById(questionId).map(questio->{
			question.setQuestionName(questio.getQuestionName());
			question.setFeedbackId(answers.getFeedbackId());
			System.out.println(question);
			return question;
		}).flatMap(questionRepository::save);
		System.out.println(finalQ.toFuture().get());
		
		return answersRepository.saveAll(answers.getAnswerList());
	}

	@Override
	public Flux<FeedbackType> getAllFeedbackTypes() {
		// TODO Auto-generated method stub
		return feedbackRepository.findAll();
	}

}
