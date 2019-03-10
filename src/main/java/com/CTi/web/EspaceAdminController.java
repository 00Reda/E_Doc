package com.CTi.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.CTi.DocStatistics;
import com.CTi.SecurityConfig;
import com.CTi.entities.Coupon;
import com.CTi.entities.Document;
import com.CTi.entities.Etudiant;
import com.CTi.entities.Filiere;
import com.CTi.entities.Professeur;
import com.CTi.entities.Roles;
import com.CTi.entities.Utilisateur;
import com.CTi.repository.DocumentRep;
import com.CTi.repository.IEspacePublicRepository;
import com.CTi.repository.Icoupons;
import com.CTi.repository.ProfesseurRep;
import com.CTi.repository.RoleRep;
import com.CTi.repository.UtilisateurRep;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class EspaceAdminController {
	
    @Autowired
    private ProfesseurRep profRep;
    @Autowired
    private Icoupons icoupons;
    @Autowired
	private IEspacePublicRepository espacePublicRep ;
    @Autowired
	private UtilisateurRep utilisateurRep;
    @Autowired
	private RoleRep roleRep ;
   
	 
    
		@RequestMapping(value="/dashboard/admin/prof")
			
		public String AllProf(Principal principal,Model model,@RequestParam(name="page",defaultValue="0")int page ) {
			Page<Professeur> professeurs= (Page<Professeur>) profRep.findAll(PageRequest.of(page, 5));
			  Collection<Professeur> professeurss=professeurs.getContent();
			  
			  Professeur p =profRep.getOne(Long.parseLong(principal.getName()));
			  Collection<Professeur> professeur =new ArrayList<>();
				   for (Professeur prof : professeurss) {
					   if(! prof.getRoles().contains(new Roles("ADMIN")) ) {
						   professeur.add(prof);
					   }
					
				}
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
		 
		 return "redirect:/dashboard/admin/bookmarks";
	 }	
     
     @RequestMapping(value="/dashboard/admin/bookmarks")
 	  public String bookmark(Model model ,Principal principal) {
 		
 		Professeur p=profRep.getOne(Long.parseLong(principal.getName()));
 		model.addAttribute("docs",p.getDocumentsEnregistres());
 		
 		return "EspaceAdmin/bookmark";
 	}
     
     @RequestMapping(value="/dashboard/admin/calendar")
	 public String calendar(Model model,Authentication auth) {
		 
		 return "EspaceAdmin/calendar";
	 }	
     
     @RequestMapping(value="/dashboard/admin/info")
     public String AdminInfo(Model model, Principal principal) {
    	 Professeur admin=profRep.getOne(Long.parseLong(principal.getName()));
    	 model.addAttribute("admin", admin);
    	 return "EspaceAdmin/info";
    	 
     }
     
     @RequestMapping(value="/dashboard/desactive") 
	    public String ActiveUser(@RequestParam(name="id")long id) {
	    	Utilisateur user=utilisateurRep.getOne(id);
	    	    user.setActive(false);
	    	    utilisateurRep.save(user);
	    	    if(user.getRoles().size()==0) {
	    	    	return "redirect:/dashboard/admin";
	    	    }
	    	   
	    	    if(user.getRoles().get(0).getRole().equals("PROF")) {
	    	    	return "redirect:/dashboard/admin/prof";
	    	    }else {
		    		return "redirect:/dashboard/admin/info/etudiant?id="+user.getId_user();
	    	    }
	    }
  @RequestMapping(value="/dashboard/active") 
	    public String desActiveUser(@RequestParam(name="id")long id) {
	       Utilisateur user=utilisateurRep.getOne(id);
	    	    user.setActive(true);
	    	    utilisateurRep.save(user);
	    	    if(user.getRoles().size()==0) {
	    	    	return "redirect:/dashboard/admin";
	    	    }
	    	    if(user.getRoles().get(0).getRole().equals("PROF")) {
	    	    	return "redirect:/dashboard/admin/prof";
	    	    }else {
		    		return "redirect:/dashboard/admin/info/etudiant?id="+user.getId_user();
	    	    }
	    	
	    	
	    }
  
  @RequestMapping(value="/dashboard/admin/edit")
  public String AdminEditForm(Model model, Principal principal) {
	 	Professeur admin=profRep.getOne(Long.parseLong(principal.getName()));
 	    model.addAttribute("admin", admin);
 	 return "EspaceAdmin/edit";
 	 
  }
  
  @RequestMapping(value="/dashboard/admin/action",method=RequestMethod.POST)
  public String AdminEditAction(Model model, Principal principal,Professeur prof) {
 	     Utilisateur admin = utilisateurRep.getOne(Long.parseLong(principal.getName()));
 	         String emailAdmin=admin.getEmail();
		 	 prof.setDocumentsEnregistres(admin.getDocumentsEnregistres());
		 	 prof.setPassword(admin.getPassword());
		 	 prof.setDate_creation(new Date());
		 	 ArrayList<Roles> role= new ArrayList<>();
		     role.add(new Roles("ADMIN"));
			 prof.setRoles(admin.getRoles());
			 prof.setId_user(admin.getId_user());
			 utilisateurRep.save(prof);
		     if(prof.getEmail().equals(emailAdmin)) { 
		    	 return "redirect:/dashboard/admin/info";
		     }
		      else 
		    	 return "redirect:/logout";
        }

	  @RequestMapping(value="/doc/admin/delete")
	 	public String deletedocAdmin(Model model , @RequestParam(name="id") long id) {
		     espacePublicRep.deleteDoc(id);
	 		
	 		return "redirect:/?info=supprimer avec succes";
	 		
	 	}
	  
	  @RequestMapping(value="/dashboard/admin/pass/action", method=RequestMethod.POST)
		 public String editetudiantpassAction(Model model,Authentication auth,@RequestParam String oldpass,@RequestParam String newpass,@RequestParam String confirmpass) {
		   Utilisateur e = utilisateurRep.getOne(Long.parseLong(auth.getName()));
	         
		     if(!newpass.equals(confirmpass)|| !SecurityConfig.verifyPass(oldpass,e.getPassword())) {
		    	 
				  return this.AdminInfo(model, auth );
				  
		     }else {
		    	 e.setPassword(SecurityConfig.crypter(newpass));
		    	 utilisateurRep.save(e);
		    	 return "redirect:/logout";
		     }
		     
		     
	   }
	  
	  @RequestMapping(value="/dashboard/admin/add")
	     public String addAdmin(Model model) {
		    model.addAttribute("admin",new Professeur());
		  return "EspaceAdmin/Admin/add";
	  }
	  
	  @RequestMapping(value="/dashboard/admin/add/action",method=RequestMethod.POST)
	     public String addAdminAction(Model model,Professeur admin) {
		    
		  try {
				 ArrayList<Roles> r=new ArrayList<>();
				 r.add(roleRep.getOne("ADMIN"));
				 Date date=new Date();
				 admin.setDate_creation(date);
				 admin.setRoles(r);
				 admin.setPassword(SecurityConfig.crypter(admin.getPassword()));
				 profRep.save(admin);
				 
			} catch (Exception e2) {      
				// TODO: handle exception
				 
				model.addAttribute("admin",new Professeur());
				 model.addAttribute("error",e2.getMessage());
				 return "EspaceAdmin/Admin/add";
			}
		  
		  return "redirect:/dashboard/admin/list";
	  }
	  

	  
	  @RequestMapping(value="/dashboard/admin/list")
	     public String listAdmin(Model model) {
		    
		  model.addAttribute("list",profRep.listAdmin(new Roles("ADMIN")))	;
		  return "EspaceAdmin/Admin/list";
	  }
	  //get all coupons
	 @RequestMapping(value="/dashboard/admin/coupons/list")
	 public String Allcoupons(Model model) {
		 model.addAttribute("coupons", icoupons.findAll());
		 return "EspaceAdmin/coupons";
	 }
	 //genereted coupon
     @RequestMapping(value="/dashboard/admin/coupons/add")
      public String GeneretedCoupons() {
 		for(int j=0; j<10; j++) {
 			String coupon=RandomStringUtils.random(15, true, true);
 			     if(icoupons.findByCoupon(coupon)==null) {
 			    	Coupon c= new Coupon();
 	 		 		c.setCoupon(coupon);;
 	 		 		 icoupons.save(c);	
 			     }
 		 			 
 		}
    	 return "redirect:/dashboard/admin/coupons/list";
     }
     
     @RequestMapping(value="/dashboard/admin/delete/coupon")
     public String deleteCoupon(@RequestParam(name="id") long id) {
    	 icoupons.deleteById(id);
    	 return "redirect:/dashboard/admin/coupons/list";
     }
}
