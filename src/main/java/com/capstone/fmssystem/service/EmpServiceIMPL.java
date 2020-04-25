package com.capstone.fmssystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

import com.FMS.Model.Employee;
import com.capstone.fmssystem.repo.Repository.EmployeeRepo;
@Service
public class EmpServiceIMPL implements EmployeeService {
	@Autowired
	EmployeeRepo empRepo;
	public Mono<Employee> save(Employee emp) {
		return empRepo.save(emp);
	}

	public Mono<Void> delete(Integer id) {
		return empRepo.deleteById(id);
	}
}
