package com.CTi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.CTi.entities.Etudiant;

public interface EtudiantRep extends JpaRepository<Etudiant, Long>{
    
	
	@Query("select etudiant from Etudiant etudiant order by etudiant.filiere.nom desc")
	public Page<Etudiant> findAll(Pageable page);
}
   