package com.capstone.FMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

import com.FMS.Model.Employee;
import com.capstone.fmssystem.service.EmployeeService;
import com.capstone.fmssystem.service.EventServiceImpl;

@RestController
public class EmployeeController {
	@Autowired
	private EmployeeService empService;
	@PostMapping(value = "/addEmp")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Employee> addEmployee(@RequestBody Employee emp) {
		return empService.save(emp);
		
	}
	@DeleteMapping("/remove/{id}")
	public Mono<Void> deleteById(@PathVariable Integer id){
		return empService.delete(id);
		
	}
}
