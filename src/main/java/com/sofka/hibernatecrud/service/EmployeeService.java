package com.sofka.hibernatecrud.service;

import com.sofka.hibernatecrud.model.Employee;
import com.sofka.hibernatecrud.model.Project;
import com.sofka.hibernatecrud.repository.IEmployeeJpaRepository;
import com.sofka.hibernatecrud.repository.IProjectJpaRepository;
import com.sofka.hibernatecrud.repository.IRoleJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmployeeService {

    @Autowired
    IEmployeeJpaRepository employeeRepo;

    @Autowired
    IProjectJpaRepository repoProject;

    @Autowired
    IRoleJpaRepository repoRole;

    public Optional<Employee> findById(Long id){
        return employeeRepo.findById(id);
    }

    public List<Employee> findAll(){
        return employeeRepo.findAll();
    }

    public Employee save(Employee employee, String projectName, String roleName){

        employee = addProject(employee, projectName);
        employee = addRole(employee, roleName);
        return employeeRepo.save(employee);
    }

    private Employee addRole(Employee employee, String roleName) {
        try {
            if (roleName != null){
                employee.setRole(repoRole.findByName(roleName));
            }else if ((roleName == null) && (employee.getRole() == null)){
                employee.setRole(repoRole.findByName("dummy_role"));
            }
            return employee;
        }catch(Exception e){
            return employee;
        }
    }

    private Employee addProject(Employee employee, String projectName) {
        try {
            if (projectName != null){
                Project project = repoProject.findByName(projectName);
                if (!isInProjects(employee.getProjects(), project.getName())){
                    employee.getProjects().add(project);
                }
            } else if ((projectName == null) && (employee.getProjects().isEmpty())){
                employee.getProjects().add(repoProject.findByName("dummy_proj"));
            }
            return employee;
        }catch (Exception e){
            return employee;
        }
    }

    private boolean isInProjects(List<Project> projects, String projectName) {
        return projects.stream().filter(x->x.getName().equals(projectName)).count() != 0;
    }

    public Employee findByEmployeeId(String employeeId){
        return employeeRepo.findByEmployeeId(employeeId);
    }

    public boolean deleteById(Long id){
        try {
            employeeRepo.deleteById(id);
            return true;
        }catch (Exception err){
            return false;
        }
    }

    public List<Employee> findByLastName(String lastName){
        return employeeRepo.findByLastName(lastName);
    }
}
