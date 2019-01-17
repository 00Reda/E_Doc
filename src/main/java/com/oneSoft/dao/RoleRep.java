package com.oneSoft.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneSoft.entities.Roles;

public interface RoleRep extends JpaRepository<Roles, String>{

}
