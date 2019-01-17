package com.oneSoft.web;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import antlr.collections.impl.Vector;

@Controller
public class AuthentificationController {
	
	
     @RequestMapping(value="/login")
	 
	 public String login(Authentication auth) {
    	 
    	 if(auth!=null) {
    		 Collection<GrantedAuthority> roles= (Collection<GrantedAuthority>) auth.getAuthorities();

    		  for (GrantedAuthority role : roles) {
				
    			  if(role.getAuthority().equals("ROLE_ETUDIANT")) {
    				  return "redirect:/dashboard/etudiant";
    			  }else if(role.getAuthority().equals("ROLE_PROF")) {
    				  return "redirect:/dashboard/prof";
    			  }else if(role.getAuthority().equals("ROLE_ADMIN")) {
    				  return "redirect:/dashboard/admin";
    			  }
    			  
			}
 
    	 }
    	 
		 return "authentification/login";
	 }
     
      @RequestMapping(value="/dashboard")
	 
	 public String dashboard(Authentication auth) {
    	 
    	 if(auth!=null) {
    		 Collection<GrantedAuthority> roles= (Collection<GrantedAuthority>) auth.getAuthorities();

    		  for (GrantedAuthority role : roles) {
				
    			  if(role.getAuthority().equals("ROLE_ETUDIANT")) {
    				  return "redirect:/dashboard/etudiant";
    			  }else if(role.getAuthority().equals("ROLE_PROF")) {
    				  return "redirect:/dashboard/prof";
    			  }else if(role.getAuthority().equals("ROLE_ADMIN")) {
    				  return "redirect:/dashboard/admin";
    			  }
    			  
			}
 
    	 }
    	 
		 return "authentification/login";
	 }

     @RequestMapping(value="/logout")
	 
	 public String logout(HttpServletRequest request,HttpServletResponse response) {
    	 HttpSession session= request.getSession(false);
    	    SecurityContextHolder.clearContext();
    	         session= request.getSession(false);
    	        if(session != null) {
    	            session.invalidate();
    	        }
    	        for(Cookie cookie : request.getCookies()) {
    	            cookie.setMaxAge(0);
    	        }
    	        
    	        return "redirect:/login";
	 }
     
     @RequestMapping(value="/403")
     public String accessDenied() {
    	 return "authentification/403";
     }
     
     
     
     
    
	 
    @RequestMapping(value="/dashboard/prof")
	 public String professeur() {
    	 
    	 return "redirect:/dashboard/prof/home";
	 }
    
    @RequestMapping(value="/dashboard/admin")
	 public String admin() {
		 return "EspaceAdmin/home";
	 }
    
    

}
