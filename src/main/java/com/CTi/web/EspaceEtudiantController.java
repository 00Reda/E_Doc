package com.CTi.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.CTi.SecurityConfig;
import com.CTi.entities.Document;
import com.CTi.entities.Etudiant;
import com.CTi.entities.Filiere;
import com.CTi.entities.Roles;
import com.CTi.entities.Utilisateur;
import com.CTi.repository.EtudiantRep;
import com.CTi.repository.FiliereRep;
import com.CTi.repository.IEspacePublicRepository;
import com.CTi.repository.UtilisateurRep;
@Controller
public class EspaceEtudiantController {
	
	@Autowired
	 private UtilisateurRep userRep ;
	
	@Autowired
	 private EtudiantRep etudiantRep ;
	
	@Autowired
	 private IEspacePublicRepository espacePublicRep ;
	@Autowired
	 private FiliereRep filiereRep ;
	
	@RequestMapping(value="/dashboard/etudiant/bookmark")
	 public String bookmark(Model model,Authentication auth) {
		 
		 return "redirect:/dashboard/etudiant";
	 }
	
	 @RequestMapping(value="/dashboard/etudiant")
	 public String etudiant(Model model,Authentication auth) {
		 
		 Utilisateur e = userRep.getOne(Long.parseLong(auth.getName()));
		Collection<Document> docenregistrer=e.getDocumentsEnregistres();
		 
		 model.addAttribute("bookmark", docenregistrer);
		 return "EspaceEtudiant/home";
	 }
	
	
	@RequestMapping(value="/dashboard/etudiant/info", method=RequestMethod.GET)
	 public String infoetudiant(Model model,Authentication auth,boolean exits) {
		 boolean errors=exits;
		 Etudiant e = etudiantRep.getOne(Long.parseLong(auth.getName()));
		 model.addAttribute("etudiant",e);
		
		 model.addAttribute("errors",errors);
		 return "EspaceEtudiant/info";
	 }
   
   @RequestMapping(value="/dashboard/etudiant/edit", method=RequestMethod.GET)
	 public String editetudiantForm(Model model,Authentication auth) {
	   
	   Etudiant e = etudiantRep.getOne(Long.parseLong(auth.getName()));
        ArrayList<Filiere> filieres=(ArrayList<Filiere>) filiereRep.findAll();
		 
		 model.addAttribute("filieres",filieres);
		 model.addAttribute("etudiant",e);
		 
		 return "EspaceEtudiant/edit";
	 }
  
   
   @RequestMapping(value="/dashboard/etudiant/action", method=RequestMethod.POST)
	 public String editetudiantAction(Model model, Etudiant etudiant,Authentication auth) {
	   Utilisateur e = userRep.getOne(Long.parseLong(auth.getName()));
	      String emailetudiant=e.getEmail();
	     etudiant.setDocumentsEnregistres(e.getDocumentsEnregistres());
	     etudiant.setPassword(e.getPassword());
	     
	         etudiant.setDate_creation(new Date());
	         ArrayList<Roles> role= new ArrayList<>();
             role.add(new Roles("ETUDIANT"));
             etudiant.setId_user(e.getId_user());
	         etudiant.setRoles(role);
	    	 etudiantRep.save(etudiant);
	    	 if(etudiant.getEmail().equals(emailetudiant))
			 return "redirect:/dashboard/etudiant";
	    	 else 
	    		 return "redirect:/logout";	 
	     
	     
	    
	 }
   
   @RequestMapping(value="/dashboard/etudiant/pass/action", method=RequestMethod.POST)
	 public String editetudiantpassAction(Model model,Authentication auth,@RequestParam String oldpass,@RequestParam String newpass,@RequestParam String confirmpass) {
	   Utilisateur e = userRep.getOne(Long.parseLong(auth.getName()));
         
	     if(!newpass.equals(confirmpass)|| !SecurityConfig.verifyPass(oldpass,e.getPassword())) {
	    	 
			  return this.infoetudiant(model, auth,true );
			  
	     }else {
	    	 e.setPassword(SecurityConfig.crypter(newpass));
	    	 userRep.save(e);
	    	 return "redirect:/logout";
	     }
	     
	     
}
   @RequestMapping(value="/dashboard/admin/etudiants", method=RequestMethod.GET)
	  public String Alletudiants(Model model,@RequestParam(name="page", defaultValue="0")int page) {
	     Page<Etudiant> etudiants = etudiantRep.findAll(PageRequest.of(page, 20));
	     List<Etudiant> etudiant = etudiants.getContent();
	     int total= etudiants.getTotalPages();
		 model.addAttribute("etudiants",etudiant);
		 model.addAttribute("total",total);
		 
		 return "EspaceAdmin/Etudiant/info";
	 } 
   
   @RequestMapping(value="/dashboard/admin/delete/etudiant")
   public String deleteEtudiant(@RequestParam(name="id") long id) {
	    
	 userRep.deleteById(id); 
	 
 	 return "redirect:/dashboard/admin/etudiants";
  }
   
   @RequestMapping(value="/dashboard/admin/info/etudiant")
   public String InfoEtudiant(@RequestParam(name="id") long id,Model model, @RequestParam(name="page" ,defaultValue="0")int page) {
	Etudiant etudiant= etudiantRep.getOne(id); 
	
	 int nombretotal=etudiant.getDocumentsEnregistres().size();
	 Collection<Document> docenregistrer=etudiant.getDocumentsEnregistres();
	 model.addAttribute("documents", docenregistrer);
	 model.addAttribute("etudiant", etudiant);
	 model.addAttribute("nombretotal", nombretotal);
	 
	 
 	 return "EspaceAdmin/Etudiant/infoOfetudiant";
  }
	    
   @RequestMapping(value="/dashboard/etudiant/calendar")
	 public String calendar(Model model,Authentication auth) {
		 
		 return "EspaceEtudiant/calendar";
	 }	

   
   

}
