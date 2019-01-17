package com.oneSoft.web;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oneSoft.dao.IEspacePublicRepository;

import com.oneSoft.dao.ProfesseurRep;
import com.oneSoft.entities.Document;
import com.oneSoft.entities.Etudiant;
import com.oneSoft.entities.Filiere;
import com.oneSoft.entities.Professeur;

@Controller
public class EspaceAdminController {
    @Autowired
    private ProfesseurRep profRep;
    @Autowired
	 private IEspacePublicRepository espacePublicRep ;
    
    
		@RequestMapping(value="/dashboard/admin/prof")
			
			public String AllProf(Model model,@RequestParam(name="page",defaultValue="0")int page ) {
				Page<Professeur> professeurs= (Page<Professeur>) profRep.findAll(PageRequest.of(page, 5));
				  Collection<Professeur> professeur=professeurs.getContent();
				  
				   model.addAttribute("total", professeurs.getTotalPages() );
				   model.addAttribute("professeur", professeur );
				   
				   
				return "EspaceAdmin/Prof/info";
			}
		
		@RequestMapping(value="/dashboard/admin/prof/documents")
		
		public String documentsProf(Model model,@RequestParam(name="id")long id,@RequestParam(name="page",defaultValue="0")int page ) {
			
			 Professeur prof=profRep.getOne(id);
			 Page<Document> documents= (Page<Document>) espacePublicRep.findByProprietaire(prof, PageRequest.of(page, 9));
			 Collection<Document> document=documents.getContent();
			 boolean empty=false;
			 if(documents.isEmpty()) {
				 empty=true;
			 }
			 model.addAttribute("empty", empty);
			 model.addAttribute("prof", prof);
			 model.addAttribute("total", documents.getTotalPages());
			 model.addAttribute("documents", document);
			return "EspaceAdmin/Prof/documentprof";
		}
		
		@RequestMapping(value="/dashboard/admin/delete/prof")
		
		public String deleteProf(Model model,@RequestParam(name="id") long id ) {
		      profRep.deleteById(id);
			  return "redirect:/dashboard/admin/prof";
		}
     @RequestMapping(value="/dashboard/admin/prof/document/delete")
		
		public String deleteDoc(Model model,@RequestParam(name="id")long id ,@RequestParam(name="email")String email) {
    	      espacePublicRep.deleteById(id);
			  return "redirect:/dashboard/admin/prof/documents?id="+email;
		}
		
     @RequestMapping(value="/dashboard/admin/bookmark")
	 public String bookmark(Model model,Authentication auth) {
		 
		 return "redirect:/dashboard/etudiant";
	 }	
     
     @RequestMapping(value="/dashboard/admin/calendar")
	 public String calendar(Model model,Authentication auth) {
		 
		 return "EspaceAdmin/calendar";
	 }	
     
    

}
