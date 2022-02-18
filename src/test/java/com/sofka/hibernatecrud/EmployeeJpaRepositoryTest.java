package com.sofka.hibernatecrud;

import com.sofka.hibernatecrud.model.Employee;
import com.sofka.hibernatecrud.model.Project;
import com.sofka.hibernatecrud.model.Role;
import com.sofka.hibernatecrud.repository.IEmployeeJpaRepository;
import com.sofka.hibernatecrud.repository.IProjectJpaRepository;
import com.sofka.hibernatecrud.repository.IRoleJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeJpaRepositoryTest {

    @Autowired
    private IEmployeeJpaRepository repoEmployee;

    @Autowired
    private IRoleJpaRepository repoRole;

    @Autowired
    private IProjectJpaRepository repoProject;

    @Test
    public void saveEmployee(){

        Role admin = new Role("ROLE_ADMIN");
        Role dev = new Role("ROLE_DEV");

        admin = repoRole.save(admin);
        dev = repoRole.save(dev);

        Project proj1  = new Project("proj1");
        Project proj2  = new Project("proj2");
        Project proj3  = new Project("proj3");

        proj1 = repoProject.save(proj1);
        proj2 = repoProject.save(proj2);
        proj3 = repoProject.save(proj3);

        Employee john = new Employee("John", "Doe", "emp_001", dev);
        Employee jane = new Employee("Jane", "Doe", "emp_002", admin);
//        Employee homer = new Employee("Homer", "Simpson", "emp_003");

        john.getProjects().add(proj1);
        john.getProjects().add(proj2);

        jane.getProjects().add(proj1);
        jane.getProjects().add(proj2);
        jane.getProjects().add(proj3);

        repoEmployee.save(john);
        repoEmployee.save(jane);
//        repoEmployee.save(homer);

//      Synchronizes the repository with what is in the database
        repoEmployee.flush();

        Employee emp_002  = repoEmployee.findByEmployeeId("emp_002");
        Assertions.assertEquals("Jane", emp_002.getFirstName());
        Assertions.assertEquals(2, repoEmployee.findAll().size());
        Assertions.assertEquals(admin, emp_002.getRole());
    }
}
