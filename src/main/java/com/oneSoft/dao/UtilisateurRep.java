package com.oneSoft.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneSoft.entities.Professeur;
import com.oneSoft.entities.Utilisateur;

public interface UtilisateurRep extends JpaRepository<Utilisateur, Long>{

	
}
