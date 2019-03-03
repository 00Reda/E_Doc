package com.CTi.web;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.CTi.entities.Document;
import com.CTi.entities.Etudiant;
import com.CTi.entities.Filiere;
import com.CTi.repository.EtudiantRep;
import com.CTi.repository.FiliereRep;

import net.bytebuddy.implementation.bind.MethodNameEqualityResolver;

@Controller
public class FilièreController {
	@Autowired
	 private EtudiantRep etudiantRep ;
	
	@Autowired
	 private FiliereRep filiereRep ;
	
	
	@RequestMapping(value="/dashboard/admin/filieres")
	
	public String AllFilieres(Model model,@RequestParam(name="page",defaultValue="0")int page ) {
		Page<Filiere> filieres= (Page<Filiere>) filiereRep.findAll(PageRequest.of(page, 5));
		  Collection<Filiere> filiere=filieres.getContent();
		  
		   model.addAttribute("total", filieres.getTotalPages() );
		   model.addAttribute("filieres", filiere );
		   
		   
		return "EspaceAdmin/Filière/info";
	}
	
	@RequestMapping(value="/dashboard/admin/filiere/etudiants")
	public String AllEtudiantFiliere(Model model,@RequestParam(name="id")int id,@RequestParam(name="page" ,defaultValue="0")int page) {
		 Filiere filiere=filiereRep.findById(id);
		 
		 List<Etudiant> etudiants=filiere.getEtudiants();
		 int total=0;
		 boolean empty=false;;
		 if(etudiants.isEmpty()) {
			 empty=true;
		 }else {
			 PageImpl<Etudiant> pages =new PageImpl<>(etudiants,PageRequest.of(page, 5) ,5);
			 Page<Etudiant> pag=(Page<Etudiant>) pages;
			 total=pages.getTotalPages();
			 etudiants=pag.getContent();
		 }
		 
		 model.addAttribute("filiere", filiere);
		 model.addAttribute("total", total  );
		 model.addAttribute("etudiants", etudiants);
		 model.addAttribute("empty", empty);
		
		return "EspaceAdmin/Filière/etudiantfiliere";
	}
	@RequestMapping(value="/dashboard/admin/filiere")
	public String EditfiliereForm(Model model,@RequestParam(name="id")int id) {
		 Filiere filiere=filiereRep.findById(id);
		 model.addAttribute("filiere", filiere);
		 return "EspaceAdmin/Filière/edit";
	}
	@RequestMapping(value="/dashboard/admin/filiere/action", method=RequestMethod.POST)
	public String EditfiliereAction(Model model, Filiere f) {
		 Filiere filiere=filiereRep.findById(f.getId());
		 f.setEtudiants(filiere.getEtudiants());
		 filiereRep.save(f);
		 return "redirect:/dashboard/admin/filieres";
	}
	
	@RequestMapping(value="/dashboard/admin/filiere/add", method=RequestMethod.GET)
	public String AddfiliereForm(Model model ) {
		 
		 model.addAttribute("filiere", new Filiere());
		 return "EspaceAdmin/Filière/ajout";
	}
	@RequestMapping(value="/dashboard/admin/filiere/add/action", method=RequestMethod.POST)
	public String AddfiliereAction(Model model, Filiere f) {
		 filiereRep.save(f);
		 return "redirect:/dashboard/admin/filieres";
	}
	@RequestMapping(value="/dashboard/admin/delete/filiere")
	public String deletefiliereAction(Model model,@RequestParam(name="id")long id ) {
		 filiereRep.deleteById(id);;
		 return "redirect:/dashboard/admin/filieres";
	}
	
}
