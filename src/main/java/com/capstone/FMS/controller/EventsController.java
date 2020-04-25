package com.capstone.FMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.FMS.Model.Event;
import com.capstone.fmssystem.service.EventServiceImpl;

@RestController
public class EventsController {

	@Autowired
	private EventServiceImpl eventService;

	@GetMapping("/fetchEvents")
	public Flux<Event> getEvents() {
		return eventService.getAllEvents();
	}

	@GetMapping("/retrieveBVH/{vh}")
	public Flux<Event> retrieveEventsByVolunteerHours(@PathVariable int vh) {
		return eventService.searchByVolunteerHours(vh);
	}
	@GetMapping("/fetchEvents/{eventId}")
	public Flux<Event> getEventsById(@PathVariable String eventId) {
		return eventService.searchEventsById(eventId);
	}
	
	@GetMapping("/retrieveById/{id}")
	public Mono<Event> getEventsById(@PathVariable int id) {
		return eventService.findEventById(id);
	}

	@GetMapping("/sendMail")
	public Mono<String> sendEmail() {
		return eventService.SendMail();
	}
}
