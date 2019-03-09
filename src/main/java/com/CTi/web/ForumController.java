package com.CTi.web;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.CTi.EDocApplication;
import com.CTi.entities.Etudiant;
import com.CTi.entities.Question;
import com.CTi.entities.QuestionRepense;
import com.CTi.entities.Utilisateur;
import com.CTi.repository.EtudiantRep;
import com.CTi.repository.ForumQuestionRep;
import com.CTi.repository.ForumRepenseRep;
import com.CTi.repository.UtilisateurRep;

@Controller
public class ForumController {
	
	public static int NUMBER_POINTS_TO_ADD_AFTER_VALIDATION =10;
	public static int MAX_NUMBER_POINTS_TO_CHANGE =100;
	@Autowired
	private UtilisateurRep userRep;
	
	@Autowired
	private EtudiantRep etudiantRep;
	
	@Autowired
	private ForumQuestionRep questionRep;
	
	@Autowired
	private ForumRepenseRep respenseRep;
	
	@RequestMapping("/com/forum")
	public String home(Model model,Principal principal) {
		
		List<Question> list=questionRep.findAll();
		model.addAttribute("questions",list);
	    model.addAttribute("user",userRep.getOne(Long.parseLong(principal.getName())));
	    Etudiant etudiant=null;
	    if(etudiantRep.existsById(Long.parseLong(principal.getName()))) {
	    	etudiant=etudiantRep.getOne(Long.parseLong(principal.getName()));
	    }
	    model.addAttribute("etudiant",etudiant);
	    model.addAttribute("question",new Question());
		return "Forum/home";
	}
	
	@RequestMapping("/com/forum/show")
	public String show(Model model,Principal principal,@PathParam("id") String id) {
		
		if(id.equals("") || id.equals("0")) return "redirect:/com/forum";
		
		Question q=questionRep.getOne(Long.parseLong(id));
		model.addAttribute("e",q);
		model.addAttribute("user",userRep.getOne(Long.parseLong(principal.getName())));

		Etudiant etudiant=null;
	    if(etudiantRep.existsById(Long.parseLong(principal.getName()))) {
	    	etudiant=etudiantRep.getOne(Long.parseLong(principal.getName()));
	    }
	    model.addAttribute("etudiant",etudiant);
	    
		Utilisateur u=userRep.getOne(Long.parseLong(principal.getName()));
		System.err.println(u.getId_user());
		model.addAttribute("repense",new QuestionRepense());
		return "Forum/show";
	}
	
	
	@RequestMapping("/com/forum/question/delete")
	public String deleteQuestion(Model model,Principal principal,@PathParam("id") String id) {
		
		if(id.equals("") || id.equals("0")) return "redirect:/com/forum";
		
		Utilisateur user=userRep.getOne(Long.parseLong(principal.getName()));
		if(user.getRoles().get(0).getRole().equals("ADMIN")) {
			questionRep.deleteById(Long.parseLong(id));
		}else {
			Question q=questionRep.getOne(Long.parseLong(id));
			if(q.getOwner().getEmail().equals(user.getEmail())) {
				questionRep.delete(q);
			}
		}

		return "redirect:/com/forum";
	}
	
	@RequestMapping("/com/forum/reponse/delete")
	public String deleteRespense(Model model,Principal principal,@PathParam("id") String id) {
		
		if(id.equals("") || id.equals("0")) return "redirect:/com/forum";
		
		Utilisateur user=userRep.getOne(Long.parseLong(principal.getName()));
		QuestionRepense q=respenseRep.getOne(Long.parseLong(id));
		if(user.getRoles().get(0).getRole().equals("ADMIN")) {
			respenseRep.delete(q);
		}else {
			
			if(q.getRependeur().getEmail().equals(user.getEmail())) {
				respenseRep.delete(q);
			}
		}

		return "redirect:/com/forum/show?id="+q.getQuestion().getId_question();
	}
	
	@RequestMapping(value="/com/forum/question/add", method=RequestMethod.POST)
	public String addQuestionAction(Model model,Principal principal,Question q) {
		
		q.setDateAjout(EDocApplication.userDataFormat.format(new Date()));
		Utilisateur user = userRep.getOne(Long.parseLong(principal.getName())) ;
		q.setOwner(user);
		Etudiant etudiant=null;
	    if(etudiantRep.existsById(Long.parseLong(principal.getName()))) {
	    	etudiant=etudiantRep.getOne(Long.parseLong(principal.getName()));
	        if(etudiant.getNb_question()!=0) {
				etudiant.setNb_question(etudiant.getNb_question()-1);
				etudiantRep.flush();
				questionRep.save(q);
			}else {
				
				return "redirect:/com/forum/sub";
			}
		}else {
			
			questionRep.save(q);
		}
		
		return "redirect:/com/forum";
	}
	
	@RequestMapping(value="/com/forum/repense/add", method=RequestMethod.POST)
	public String addReplyAction(Model model,Principal principal,QuestionRepense q,@PathParam("id") String id) {
		
		if(id.equals("") || id.equals(0)) return "redirect:/com/forum";
		
		q.setDateAjout(EDocApplication.userDataFormat.format(new Date()));
		q.setRependeur(userRep.getOne(Long.parseLong(principal.getName())));
		q.setQuestion(questionRep.getOne(Long.parseLong(id)));
		respenseRep.save(q);
		
		return "redirect:/com/forum/show?id="+id;
	}
	
	@RequestMapping(value="/com/forum/repense/edit", method=RequestMethod.POST)
	public String editReplyAction(Model model,Principal pricipal,QuestionRepense q,@PathParam("id") String id) {
		
		if(id.equals("") || id.equals(0)) return "redirect:/com/forum";
		
		QuestionRepense rep=respenseRep.getOne(Long.parseLong(id));
		rep.setRepense(q.getRepense());
		respenseRep.saveAndFlush(rep);
		
		return "redirect:/com/forum/show?id="+rep.getQuestion().getId_question();
	}
	
	@RequestMapping(value="/com/forum/reponse/valider", method=RequestMethod.GET)
	public String ValidateResponse(Principal principal,@PathParam("id") String id) {
		
		if(id.equals("") || id.equals(0)) return "redirect:/com/forum";
		Utilisateur user = userRep.getOne(Long.parseLong(principal.getName())) ;
		QuestionRepense q= this.respenseRep.getOne(Long.parseLong(id));
		if(q==null) return "redirect:/com/forum";
		if(q.getQuestion().getOwner().getId_user()==user.getId_user() && q.getRependeur().getId_user()!=user.getId_user()) {
			
			Etudiant etudiant=null;
		    if(etudiantRep.existsById(Long.parseLong(principal.getName()))) {
		    	etudiant=etudiantRep.getOne(Long.parseLong(principal.getName()));
		    }
			
			if(etudiant==null) return "redirect:/com/forum";
			q.setValider(true);
			this.respenseRep.flush();
			etudiant.setNb_point(etudiant.getNb_point()+NUMBER_POINTS_TO_ADD_AFTER_VALIDATION);
			if(etudiant.getNb_point()==MAX_NUMBER_POINTS_TO_CHANGE) {
				etudiant.setNb_point(0);
				etudiant.setNb_question(etudiant.getNb_question()+1);
			}
			etudiantRep.flush();
		}
		
		return "redirect:/com/forum/show?id="+q.getQuestion().getId_question();
	}
	
	@RequestMapping(value="/com/forum/question/edit", method=RequestMethod.POST)
	public String editQuestionAction(Model model,Principal pricipal,Question q,@PathParam("id") String id) {
		
		if(id.equals("") || id.equals(0)) return "redirect:/com/forum";
		
		Question ques=questionRep.getOne(Long.parseLong(id));
		ques.setSujet(q.getSujet());
		ques.setDescription(q.getDescription());
		questionRep.saveAndFlush(ques);
		
		return "redirect:/com/forum/show?id="+ques.getId_question();
	}
	
	@RequestMapping(value="/com/forum/sub", method=RequestMethod.GET)
	public String subscriptionEnd(Model model,Principal principal) {
		
		
		Etudiant etudiant = etudiantRep.getOne(Long.parseLong(principal.getName()));
		
		model.addAttribute("user",etudiant);
		System.err.println("ok ok ok ");
		if(etudiant != null ) {
			if(etudiant.getNb_question()==0) {
                 return "Forum/sub";
			}else {
				return "redirect:/com/forum";
			}
		}
			return "redirect:/com/forum";
	}
	
	

}
