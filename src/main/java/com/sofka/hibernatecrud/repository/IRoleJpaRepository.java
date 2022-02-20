package com.sofka.hibernatecrud.repository;

import com.sofka.hibernatecrud.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleJpaRepository extends JpaRepository<Role, Long> {
    public abstract Role findByName(String name);
}
