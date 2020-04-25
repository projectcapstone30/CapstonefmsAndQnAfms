package com.capstone.fmssystem.service;

import reactor.core.publisher.Mono;

import com.FMS.Model.Employee;

public interface EmployeeService {
	public Mono<Employee> save(Employee emp);

	public Mono<Void> delete(Integer id);
}
