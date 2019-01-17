package com.oneSoft.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.oneSoft.entities.Etudiant;

public interface EtudiantRep extends JpaRepository<Etudiant, String>{
    
	
	@Query("select etudiant from Etudiant etudiant order by etudiant.filiere.nom desc")
	public Page<Etudiant> findAll(Pageable page);
}
