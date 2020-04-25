package com.capstone.fmssystem.repo.Repository;

import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

import com.FMS.Model.Employee;
@Repository
public interface EmployeeRepo  extends ReactiveCrudRepository<Employee, Integer>{

	public Mono<Employee> save(Employee emp);

	@Query("delete from  employee where id= ?")
	public Mono<Void> deleteById(Integer id);
}
