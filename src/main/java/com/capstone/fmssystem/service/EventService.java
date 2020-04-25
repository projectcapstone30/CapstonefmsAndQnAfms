package com.capstone.fmssystem.service;

import com.FMS.Model.Employee;
import com.FMS.Model.Event;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventService {
	
	public Mono<Event> findEventById(int id);

	public Mono<String> SendMail();

	public Flux<Event> getAllEvents();

	public Flux<Event> searchEventsById(String eventId);

	public Flux<Event> searchByVolunteerHours(int volunteerHours);

}
