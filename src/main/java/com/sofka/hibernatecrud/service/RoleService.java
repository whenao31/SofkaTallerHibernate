package com.sofka.hibernatecrud.service;

import com.sofka.hibernatecrud.model.Role;
import com.sofka.hibernatecrud.repository.IRoleJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    IRoleJpaRepository roleRepo;

    public List<Role> findAll(){
        return roleRepo.findAll();
    }

    public Optional<Role> findById(Long id){
        return roleRepo.findById(id);
    }

    public Role save(Role role){
        return roleRepo.save(role);
    }

    public Role findByName(String name){
        return roleRepo.findByName(name);
    }

    public boolean deleteById(Long id){
        try {
            roleRepo.deleteById(id);
            return true;
        }catch(Exception err){
            return false;
        }
    }
}
