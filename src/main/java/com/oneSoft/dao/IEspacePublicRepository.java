package com.oneSoft.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oneSoft.entities.Document;
import com.oneSoft.entities.Professeur;

public interface IEspacePublicRepository extends JpaRepository<Document,Long> {
    
	@Query("select doc from Document doc order by STR_TO_DATE(doc.dateAjout, '%d-%m-%Y') desc")
	public Page<Document> findAll(Pageable page);
	
	@Query("select doc from Document doc where doc.theme like %:th%")
	public Page<Document> findByTheme(@Param("th") String n, Pageable page);
	
	@Query("select doc from Document doc where doc.titre like %:titre% and doc.dateAjout like %:date% and doc.langue like %:langue% and doc.theme like %:theme% and doc.discipline like %:discipline% and doc.proprietaire in ( select p.email from Utilisateur p where p.nom like %:nom% or p.prenom like %:nom% )")
	public List<Document> AdvancedSearch(@Param("titre") String titre ,@Param("date") String date, @Param("nom") String auteur ,@Param("langue") String langue , @Param("theme") String theme , @Param("discipline") String discipline);
	
	@Query("select doc from Document doc where doc.proprietaire= :p order by STR_TO_DATE(doc.dateAjout, '%d-%m-%Y') desc")
	public Page<Document> findByProprietaire(@Param("p") Professeur p,Pageable page);
	
	
}
