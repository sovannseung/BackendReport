package com.javatechie.report.controller;

import com.javatechie.report.entity.Employee;
import com.javatechie.report.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeRepository repository;

    //@GetMapping("/getEmployees")
    public List<Employee> getEmployees() {

        return repository.findAll();
    }
}
