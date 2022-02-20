package com.sofka.hibernatecrud.service;

import com.sofka.hibernatecrud.model.Project;
import com.sofka.hibernatecrud.repository.IProjectJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    IProjectJpaRepository projectRepo;

    public List<Project> findAll(){
        return projectRepo.findAll();
    }

    public Optional<Project> findById(Long id){
        return projectRepo.findById(id);
    }

    public Project save(Project project){
        return projectRepo.save(project);
    }
    public Project findByName(String name){
        return projectRepo.findByName(name);
    }

    public boolean deleteById(Long id){
        try {
            projectRepo.deleteById(id);
            return true;
        }catch (Exception err){
            return false;
        }
    }
}
