package com.sofka.hibernatecrud.controller;

import com.sofka.hibernatecrud.model.Project;
import com.sofka.hibernatecrud.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project-api")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAllProjects(){
        List<Project> projects = projectService.findAll();
        if (!projects.isEmpty()){
            return new ResponseEntity<>(projects, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/projects/{name}")
    public ResponseEntity<Project> getProjectByName(@PathVariable("name") String name){
        try {
            Project project = projectService.findByName(name);
            if (project != null){
                return new ResponseEntity<>(project, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/projects")
    public ResponseEntity<Project> createProject(@RequestBody Project project){
        try {
            return new ResponseEntity<>(projectService.save(project), HttpStatus.OK);
        }catch(Exception err){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/projects")
    public ResponseEntity<Project> updateProject(@RequestParam("old_name") String old_name,
                                           @RequestParam("new_name") String new_name){
        try {
            Project project = projectService.findByName(old_name);
            if (project != null){
                project.setName(new_name);
                projectService.save(project);
                return new ResponseEntity<>(project, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/projects/{name}")
    public ResponseEntity<String> deleteProjectByName(@PathVariable("name") String name){
        try {
            Project project = projectService.findByName(name);
            return getDeleteProjectResponseEntity(project);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<String> getDeleteProjectResponseEntity(Project project) {
        if (project != null){
            if (projectService.deleteById(project.getId())){
                return new ResponseEntity<>("Project '" + project.getName() + "' deleted", HttpStatus.OK);
            }
            return new ResponseEntity<>("Project '" + project.getName() + "' could not be deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Project not found to be deleted", HttpStatus.NOT_FOUND);
    }

}
