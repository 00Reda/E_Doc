package com.oneSoft.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import groovy.transform.Generated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Coupon implements Serializable {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String hash;
	private boolean etat;

}
