package com.CTi.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type_user",discriminatorType=DiscriminatorType.STRING)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected long id_user;
    
	@Column(unique=true)
	protected String email;
	protected String nom;
	protected String prenom;
	protected Date date_creation;
	protected String password;
	
	@Column(length=100)
	protected String token ;

	protected boolean active=true;
	
	@OneToMany(mappedBy="proprietaire",cascade=CascadeType.DETACH)
	
	protected List<Document> documents;
	
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinTable(
    		 name="bookmark",
    		 joinColumns=@JoinColumn(name="id_user", referencedColumnName="id_user"),
    		 inverseJoinColumns=@JoinColumn(name="id_document" , referencedColumnName="id_document")
    		)
	
	protected List<Document> documentsEnregistres;
	
    @ManyToMany
    @JoinTable(
    		 name="users_roles",
    		 joinColumns=@JoinColumn(name="username", referencedColumnName="id_user"),
    		 inverseJoinColumns=@JoinColumn(name="role" , referencedColumnName="role")
    		)
	
	protected List<Roles> roles;
	

}