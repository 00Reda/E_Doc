package com.CTi.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
	
public class QuestionRepense {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id_QR ;
	
	@Column(length=1000)
	private String repense ;
	private String dateAjout ;
	private boolean valiser = false ; 
	
	@ManyToOne
	@JoinColumn(name="id_question")
	private Question question;
	
	@ManyToOne
	@JoinColumn(name="id_user")
	private Utilisateur rependeur ;
	
	

}
