package com.sofka.hibernatecrud.repository;

import com.sofka.hibernatecrud.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeJpaRepository extends JpaRepository<Employee, Long> {

    Employee findByEmployeeId(String employeeId);
}
