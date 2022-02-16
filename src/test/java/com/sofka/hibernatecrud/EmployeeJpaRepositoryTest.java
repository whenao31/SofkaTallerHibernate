package com.sofka.hibernatecrud;

import com.sofka.hibernatecrud.model.Employee;
import com.sofka.hibernatecrud.repository.IEmployeeJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeJpaRepositoryTest {

    @Autowired
    private IEmployeeJpaRepository repoEmployee;

    @Test
    public void saveEmployee(){
        Employee john = new Employee("John", "Doe", "emp_001");
        Employee jane = new Employee("Jane", "Doe", "emp_002");
        Employee homer = new Employee("Homer", "Simpson", "emp_003");

        repoEmployee.save(john);
        repoEmployee.save(jane);
        repoEmployee.save(homer);

//      Synchronizes the repository with what is in the database
        repoEmployee.flush();

        Assertions.assertEquals(3, repoEmployee.findAll().size());
    }
}
