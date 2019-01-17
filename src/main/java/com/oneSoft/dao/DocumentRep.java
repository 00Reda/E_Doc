package com.oneSoft.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oneSoft.entities.Document;
import com.oneSoft.entities.Professeur;

public interface DocumentRep extends JpaRepository<Document, Long>{

	@Transactional
	@Modifying
	@Query(" delete from Document where id_document = :id and proprietaire = :user")
	public void delete(@Param("id") long id , @Param("user") Professeur p);
	
	@Query(" select docs from Document docs where docs.id_document = :id and docs.proprietaire = :user ")
	public Document findByIdAndOwner(@Param("id") long id , @Param("user") Professeur p);
	
}
