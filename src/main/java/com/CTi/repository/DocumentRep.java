package com.CTi.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CTi.DocStatistics;
import com.CTi.entities.Document;
import com.CTi.entities.Professeur;

public interface DocumentRep extends JpaRepository<Document, Long>{

	@Transactional
	@Modifying
	@Query(" delete from Document where id_document = :id and proprietaire = :user")
	public void delete(@Param("id") long id , @Param("user") Professeur p);
	
	@Query(" select docs from Document docs where docs.id_document = :id and docs.proprietaire = :user ")
	public Document findByIdAndOwner(@Param("id") long id , @Param("user") Professeur p);
	
	@Query("select new com.CTi.DocStatistics(count(*) as total, MONTH(STR_TO_DATE(doc.dateAjout, '%d-%m-%Y')) ,YEAR(STR_TO_DATE(doc.dateAjout, '%d-%m-%Y')) ) from Document doc group by 2,3 order by 3 asc	")
	public List<DocStatistics> statistics_docPerMonthYear();   
	
	@Query("select new com.CTi.DocStatistics(count(*) as total,YEAR(STR_TO_DATE(doc.dateAjout, '%d-%m-%Y')) ) from Document doc group by 2 order by 2 asc")
	public List<DocStatistics> statistics_docPerYear();   
	   
}
