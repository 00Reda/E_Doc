package com.oneSoft.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.management.relation.Role;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.oneSoft.EDocApplication;
import com.oneSoft.SecurityConfig;
import com.oneSoft.dao.DocumentRep;
import com.oneSoft.dao.EtudiantRep;
import com.oneSoft.dao.FiliereRep;
import com.oneSoft.dao.IEspacePublicRepository;
import com.oneSoft.dao.ProfesseurRep;
import com.oneSoft.dao.RoleRep;
import com.oneSoft.dao.UtilisateurRep;
import com.oneSoft.entities.Document;
import com.oneSoft.entities.Etudiant;
import com.oneSoft.entities.Filiere;
import com.oneSoft.entities.Professeur;
import com.oneSoft.entities.Roles;
import com.oneSoft.entities.Utilisateur;

@Controller
public class EspacePublicController {

	 @Autowired
	 private IEspacePublicRepository espacePublicRep ;
	 
	 @Autowired
	 private RoleRep roleRep ;
	 
	 @Autowired
	 private FiliereRep filiereRep ;
	 
	 @Autowired
	 private EtudiantRep etudiantRep ;

	 @Autowired
	 private ProfesseurRep professeurRep;
	 
	 @Autowired
	 private UtilisateurRep userRep;
	 
	 @Autowired
	 private DocumentRep docRep;
	 
	 @RequestMapping(value="/")
	 public String 	DefaultIndex(Model model)  {
		
		 return this.index(model, 1);
	 }
	 
	 @RequestMapping(value="/{page}")
	 public String 	index(Model model,@PathVariable int page)  {
		 
		 Page<Document> pages=espacePublicRep.findAll(PageRequest.of(page-1, 15));

		 Document d =new Document();
		 d.setProprietaire(new Utilisateur());
		 model.addAttribute("document",d);
		 
		 model.addAttribute("pages",pages);
		 model.addAttribute("path","/");
		 model.addAttribute("q","");
		 model.addAttribute("query","");
		
		 return "EspacePublic/Documents";
	 }
	 
	 @RequestMapping(value="/search")
	 public String 	search(Model model,@RequestParam String q)  {
		 
		 
		 return this.search(model, q, 1);
	 }
	 
	 @RequestMapping(value="/search/{page}")
	 public String 	search(Model model,@RequestParam String q,@PathVariable int page)  {
		 
		 Page<Document> pages=espacePublicRep.findByTheme(q,PageRequest.of(page-1, 15));
		 model.addAttribute("pages",pages);
		
		 Document d =new Document();
		 d.setProprietaire(new Utilisateur());
		 model.addAttribute("document",d);
		 model.addAttribute("path","/search/");
		 model.addAttribute("q",q);
		 model.addAttribute("query","?q="+q);
		 return "EspacePublic/Documents";
	 } 
	 
	 @RequestMapping(value="/advencedSearch" , method= {RequestMethod.POST,RequestMethod.GET})
	 
	 public String advencedSearch(Model model, Document d) {
		 
		 System.err.println(d.toString());   
		 
		 List<Document> list=espacePublicRep.AdvancedSearch(d.getTitre(), d.getDateAjout(), d.getProprietaire().getNom(), d.getLangue(), d.getTheme(), d.getDiscipline());
		
		 int pz=list.size();
		 if(list.size()==0) {
			 pz=1;
		 }
		 
		 PageImpl<Document> pages =new PageImpl<>(list,PageRequest.of(0, pz) ,pz);
		 
		 Page<Document> ps=(Page<Document>) pages;
		 d =new Document();
		 d.setProprietaire(new Utilisateur());
		 model.addAttribute("document",d);
		 model.addAttribute("q","");
		 model.addAttribute("advanced",true);
		  
		 model.addAttribute("pages",ps);
		 
		 return "EspacePublic/Documents";
	 }
	 
	 
	 @RequestMapping(value="/account/student")
     public String CreateStudantAccountForm(Model model) {
    	 
		 ArrayList<Filiere> filieres=(ArrayList<Filiere>) filiereRep.findAll();
		 
		 model.addAttribute("filieres",filieres);
		 model.addAttribute("etudiant",new Etudiant());
		 
    	 return "EspacePublic/etudiant";
     }
	 
	 @RequestMapping(value="/account/student/action" , method=RequestMethod.POST)
     public String CreateStudantAccountAction(Model model, Etudiant e) {
    	 
		 try {
			 ArrayList<Roles> r=new ArrayList<>();
			 r.add(roleRep.getOne("ETUDIANT"));
			 Date date=new Date();
			 e.setDate_creation(date);
			 e.setRoles(r);
			 e.setPassword(SecurityConfig.crypter(e.getPassword()));
			 etudiantRep.save(e);
			 
		} catch (Exception e2) {      
			// TODO: handle exception
			ArrayList<Filiere> filieres=(ArrayList<Filiere>) filiereRep.findAll();
			 
			 model.addAttribute("filieres",filieres);
			 model.addAttribute("etudiant",new Etudiant());
			 model.addAttribute("error",e2.getMessage());
			 return "EspacePublic/etudiant";
		}
		 
		 
    	 return "redirect:/login";
     }
	 
	 
	 @RequestMapping(value="/account/prof")
     public String CreateProfAccountForm(Model model) {
    	 
		 model.addAttribute("etudiant",new Professeur());
		 
    	 return "EspacePublic/prof";
     }
	 
	 @RequestMapping(value="/account/prof/action" , method=RequestMethod.POST)
     public String CreateProfAccountAction(Model model, Professeur e) {
    	 
		 try {
			 ArrayList<Roles> r=new ArrayList<>();
			 r.add(roleRep.getOne("PROF"));
			 e.setRoles(r);
			 Date date=new Date();
			 e.setDate_creation(date);
			 e.setPassword(SecurityConfig.crypter(e.getPassword()));
			 professeurRep.save(e);
			 
		} catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
			 model.addAttribute("etudiant",new Professeur());
			 model.addAttribute("error",e2.getMessage());
			 return "EspacePublic/prof";
		}
		 
		 
    	 return "redirect:/login";
     }
	 
	 @RequestMapping(value="/bookmark/add/{id}" , method=RequestMethod.GET)
     public String addBookmark(Principal principal,@PathVariable long id) {
    	 
		 
		 Utilisateur user=userRep.getOne(Long.parseLong(principal.getName()));
		 
		 Document doc = docRep.getOne(id);
		 
		 if(doc!=null) {
			 if(!user.getDocumentsEnregistres().contains(doc)) {
				 user.getDocumentsEnregistres().add(doc);
				 userRep.saveAndFlush(user);
			 }
		 }
		 
    	 return "redirect:/";
     }
	 
	 @RequestMapping(value="/bookmark/delete/{id}" , method=RequestMethod.GET)
     public String deleteBookmark(Principal principal,@PathVariable long id) {
    	 
		 
		 Utilisateur user=userRep.getOne(Long.parseLong(principal.getName()));
		 
		 Document doc = docRep.getOne(id);
		 
		 if(doc!=null) {
			 List<Document> docs= user.getDocumentsEnregistres();
			 if(docs.contains(doc)) {
				 user.getDocumentsEnregistres().remove(doc);
				 userRep.saveAndFlush(user);
			 }
		 }
		 
    	 return "redirect:/dashboard/"+user.getRoles().get(0).getRole().toLowerCase()+"/bookmark";
     }
	 
	 
}
