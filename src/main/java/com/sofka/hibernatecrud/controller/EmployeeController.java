package com.sofka.hibernatecrud.controller;

import com.sofka.hibernatecrud.model.Employee;
import com.sofka.hibernatecrud.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/emp-api")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping()
    public ResponseEntity<List<Employee>> getAllEmployees(){
        List<Employee> employees = employeeService.findAll();
        if (!employees.isEmpty()){
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<Employee> getByEmployeeId(@PathVariable("employeeId") String employeeId){
        try {
            Employee employee = employeeService.findByEmployeeId(employeeId);
            if (employee != null){
                return new ResponseEntity<>(employee, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee,
                                                   @RequestParam(value = "project", required = false) String project,
                                                   @RequestParam(value = "role", required = false) String role){
        try {
            return new ResponseEntity<>(employeeService.save(employee, project, role), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/employee")
    public ResponseEntity<Employee> updateByEmployeeId(@RequestParam(value = "employeeId", required = true) String employeeId,
                                                       @RequestParam(value = "project", required = false) String project,
                                                       @RequestParam(value = "role", required = false) String role){
        try {
            Employee employee = employeeService.findByEmployeeId(employeeId);
            if (employee != null){
                return new ResponseEntity<>(employeeService.save(employee, project, role), HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/employee/{employeeId}")
    public ResponseEntity<String> deleteByEmployeeId(@PathVariable("employeeId") String employeeId){
        try {
            Employee employee = employeeService.findByEmployeeId(employeeId);
            return getDeleteEmployeeResponse(employee);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<String> getDeleteEmployeeResponse(Employee employee) {
        if (employee != null){
            if (employeeService.deleteById(employee.getId())){
                return new ResponseEntity<>("Employee '" + employee.getEmployeeId() +"' removed.", HttpStatus.OK);
            }
            return new ResponseEntity<>("Employee '" + employee.getEmployeeId() +"' could not be removed.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Employee '" + employee.getEmployeeId() +"' not found to be removed.", HttpStatus.OK);
    }

}
