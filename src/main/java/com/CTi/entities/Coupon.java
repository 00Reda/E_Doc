package com.CTi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@ToString
@Entity
public class Coupon {

	@Id
	private long id_coupon ; 
	
	@Column(length=10)
	private String coupon ;
	
}
