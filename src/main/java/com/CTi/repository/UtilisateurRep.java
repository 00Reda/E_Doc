package com.CTi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CTi.entities.Professeur;
import com.CTi.entities.Utilisateur;

public interface UtilisateurRep extends JpaRepository<Utilisateur, Long>{

	
}
