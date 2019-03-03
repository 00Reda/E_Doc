package com.CTi.entities;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue(value="professeur")
public class Professeur extends Utilisateur implements Serializable{

	private String specialite;
	
	

	
	
}