package com.capstone.fmssystem.repo.Repository;

import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.FMS.Model.Employee;
import com.FMS.Model.Event;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface EventRepo extends ReactiveCrudRepository<Event, Long> {
	
	@Query("select * from events where event_id= ?")
	public Flux<Event> findAllByEvent_id(String event_id);

	@Query("select * from events where volunteer_hours= ?")
	public Flux<Event> findAllByVolunteer_hours(int vol_id);

	

}
