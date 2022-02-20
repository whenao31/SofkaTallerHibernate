package com.sofka.hibernatecrud.controller;

import com.sofka.hibernatecrud.model.Role;
import com.sofka.hibernatecrud.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role-api")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles(){
        List<Role> roles = roleService.findAll();
        if (!roles.isEmpty()){
            return new ResponseEntity<>(roles, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/roles/{name}")
    public ResponseEntity<Role> getRoleByName(@PathVariable("name") String name){
        try {
            Role role = roleService.findByName(name);
            if (role != null){
                return new ResponseEntity<>(role, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        try {
            return new ResponseEntity<>(roleService.save(role), HttpStatus.OK);
        }catch(Exception err){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/roles")
    public ResponseEntity<Role> updateRole(@RequestParam("old_name") String old_name,
                                           @RequestParam("new_name") String new_name){
        try {
            Role role = roleService.findByName(old_name);
            if (role != null){
                role.setName(new_name);
                roleService.save(role);
                return new ResponseEntity<>(role, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/roles/{name}")
    public ResponseEntity<String> deleteRoleByName(@PathVariable("name") String name){
        try {
            Role role = roleService.findByName(name);
            return getDeleteRoleResponseEntity(role);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<String> getDeleteRoleResponseEntity(Role role) {
        if (role != null){
            if (roleService.deleteById(role.getId())){
                return new ResponseEntity<>("Role '" + role.getName() + "' deleted", HttpStatus.OK);
            }
            return new ResponseEntity<>("Role '" + role.getName() + "' could not be deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Role not found to be deleted", HttpStatus.NOT_FOUND);
    }
}
