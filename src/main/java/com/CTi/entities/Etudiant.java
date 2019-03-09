package com.CTi.entities;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue(value="etudiant")
public class Etudiant extends Utilisateur implements Serializable{

	@Column(unique=true,length=15)
	private long cne;
	
	@ManyToOne
	@JoinColumn(name="id_filiere")
	private Filiere filiere;
	
		
	private int nb_point=0 ; 
	
	private int nb_question=0 ; 
	
	
	
	
	

}