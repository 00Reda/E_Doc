package com.CTi.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Document implements Serializable,Comparable<Document>{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private long id_document;
	private String titre;
	private int nombrePage;
	
	@Column(length=100)
	private String dateAjout;
	
	private String theme;
	private String langue;
	private String discipline;
	private String lien;
	private String lien_auxilliaire;
	
	
	@ManyToOne
	@JoinColumn(name="id_proprietaire" )
	private Utilisateur proprietaire;
	
	@ManyToOne
	@JoinColumn(name="filiere" )
	private Filiere filiere;
	
	@ManyToMany(mappedBy="documentsEnregistres")
	private List<Utilisateur> enregistrerPar;

	@Override
	public String toString() {
		return "Document [id_document=" + id_document + ", titre=" + titre + ", nombrePage=" + nombrePage
				+ ", dateAjout=" + dateAjout + ", theme=" + theme + ", langue=" + langue + ", discipline=" + discipline
				+ ", lien=" + lien + ", lien_auxilliaire=" + lien_auxilliaire + "]";
	}
	
	
	@Override
	public int compareTo(Document o) {
		
		// TODO Auto-generated method stub
		if(this.id_document==o.getId_document()) return 0;
		return 1;
	}
	
	
	
	
	
	
	

}