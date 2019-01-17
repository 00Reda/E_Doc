package com.oneSoft.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneSoft.entities.Filiere;

public interface FiliereRep extends JpaRepository<Filiere, Long>{

	public Filiere findById(long id);
	
	
}
