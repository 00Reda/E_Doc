package com.CTi.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Entity
public class Coupon implements Serializable {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id_coupon ; 
	
	@Column(length=15)
	private String coupon ;
	
	private boolean etat=false;
	
	private String proprietaire;
	
}
