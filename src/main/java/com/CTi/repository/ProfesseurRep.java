package com.CTi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CTi.entities.Document;
import com.CTi.entities.Professeur;
import com.CTi.entities.Roles;

public interface ProfesseurRep extends JpaRepository<Professeur, Long>{

	
	
	@Query("select doc from Document doc where doc.proprietaire = :p order by STR_TO_DATE(doc.dateAjout, '%d-%m-%Y') desc ")
	public List<Document> DocOfProf(@Param("p") Professeur p);	
	
	@Query("select prof from Professeur prof where :role member of prof.roles ")
	public List<Professeur> listAdmin(@Param("role") Roles  role);
	
}
