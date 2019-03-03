package com.CTi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CTi.entities.Filiere;

public interface FiliereRep extends JpaRepository<Filiere, Long>{

	public Filiere findById(long id);
	
	
}
