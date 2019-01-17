package com.oneSoft.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.oneSoft.EDocApplication;
import com.oneSoft.SecurityConfig;
import com.oneSoft.dao.DocumentRep;
import com.oneSoft.dao.ProfesseurRep;
import com.oneSoft.entities.Document;
import com.oneSoft.entities.Etudiant;
import com.oneSoft.entities.Filiere;
import com.oneSoft.entities.Professeur;
import com.oneSoft.entities.Roles;

@Controller
public class EspaceProfesseurController{

	@Autowired
	private ProfesseurRep professeurRep;
	@Autowired
	private DocumentRep documentRep;
	
	@RequestMapping(value="/dashboard/prof/home")
	
	public String home(Model model ,Principal principal) {
		
		Professeur p=professeurRep.getOne(Long.parseLong(principal.getName()));
		model.addAttribute("docs",professeurRep.DocOfProf(p));
		
		return "EspaceProfesseur/home";
	}
	
	@RequestMapping(value="/dashboard/prof/bookmark")
	
	public String bookmark(Model model ,Principal principal) {
		
		Professeur p=professeurRep.getOne(Long.parseLong(principal.getName()));
		model.addAttribute("docs",p.getDocumentsEnregistres());
		
		return "EspaceProfesseur/bookmark";
	}
	
	@RequestMapping(value="/users/prof/info")
	
	public String info(Model model ,Principal principal) {
		
		Professeur p=professeurRep.getOne(Long.parseLong(principal.getName()));
		model.addAttribute("p",p);
		
		return "EspaceProfesseur/info";
	}
	
    @RequestMapping(value="/users/prof/edit")
	
	public String edit(Model model ,Principal principal) {
		
		Professeur p=professeurRep.getOne(Long.parseLong(principal.getName()));
		model.addAttribute("p",p);
		
		return "EspaceProfesseur/edit";
	}
    						
    @RequestMapping(value="/users/prof/edit/action", method=RequestMethod.POST)
	
	public String editFormAction(Model model ,Principal principal,Professeur prof) {
		
    	
    	
		Professeur p=professeurRep.getOne(Long.parseLong(principal.getName()));
		prof.setRoles(p.getRoles());
		prof.setId_user(p.getId_user());
		prof.setDocumentsEnregistres(p.getDocumentsEnregistres());
		if(prof.getPassword().equals("")) {
			prof.setPassword(p.getPassword());
		}else {
			String s=SecurityConfig.crypter(prof.getPassword());
			prof.setPassword(s);
		}
		
		try {
			professeurRep.save(prof);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("p",p);
			model.addAttribute("error",e.getMessage());
			return "EspaceProfesseur/edit";
		}
		
		return "redirect:/users/prof/info";
	}
	
	
    @RequestMapping(value="/docs/prof/delete/{id}")
	
	public String delete(Model model ,Principal principal, @PathVariable long id) {
		
    	Professeur p=professeurRep.getOne(Long.parseLong(principal.getName()));
		documentRep.delete(id, p);
		return "redirect:/dashboard/prof/home";
		
	}
    
   @RequestMapping(value="/docs/prof/form")
	public String Docform(Model model ,Principal principal, @RequestParam(name="id" , required =false) String id) {
		
    	Professeur p=professeurRep.getOne(Long.parseLong(principal.getName()));
    	Document d;
		if(id==null) {
			d=new Document();
			model.addAttribute("action","add");
			model.addAttribute("btn","ajouter");
		}else {
			long idDoc;
			try {
				idDoc=Long.parseLong(id);
			} catch (Exception e) {
				// TODO: handle exception
				return "redirect:/dashboard/prof/home";
			}
			
			d=documentRep.findByIdAndOwner(idDoc, p);
			if(d==null) return "redirect:/dashboard/prof/home";
			
			model.addAttribute("action","edit/"+d.getId_document());
			model.addAttribute("btn","modifier");
		}
		
		model.addAttribute("doc",d);
		
		
		return "/EspaceProfesseur/Document/form";
		
	}
   
   @RequestMapping(value="/docs/prof/action/add")
	public String DocformAddAction(Model model ,Principal principal,Document doc ) {
		
     	Professeur p=professeurRep.getOne(Long.parseLong(principal.getName()));
     	doc.setProprietaire(p);
		doc.setDateAjout(EDocApplication.docDataFormat.format(new Date()));
     	documentRep.save(doc);
	 
	 return "redirect:/dashboard/prof/home";
		
	}
   
   @RequestMapping(value="/docs/prof/action/edit/{id}")
	public String DocformEditAction(Model model ,Principal principal,Document doc ,@PathVariable long id ) {
		
	    Document d=documentRep.getOne(id);
	    
	 
	    doc.setId_document(id);
	   
	    doc.setDateAjout(d.getDateAjout());
	    doc.setProprietaire(d.getProprietaire());
    
    	try {
    		documentRep.saveAndFlush(doc);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
	 return "redirect:/dashboard/prof/home";
		
	}
    
   @RequestMapping(value="/dashboard/prof/calendar")
	 public String calendar(Model model,Authentication auth) {
		 
		 return "EspaceProfesseur/calendar";
	 }	
	
    
	
}
