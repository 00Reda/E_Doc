package com.CTi.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Question {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id_question ;
	
	
	private String sujet ; 
	
	@Column(length=1000)
	private String description;
	
	private String dateAjout ;
	
	@ManyToOne
	@JoinColumn(name="id_user")
	private Utilisateur owner;
	
	
	@ManyToMany(mappedBy="question" , cascade = CascadeType.ALL)
	private List<QuestionRepense> repenses ;
	

}
