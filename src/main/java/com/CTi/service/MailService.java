package com.CTi.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.CTi.entities.Utilisateur;

@Service
public class MailService {
	
	@Autowired
	private JavaMailSender mailSender ;
	
	
	public void sendMail(Utilisateur user, String subject , String content) throws Exception {
		
		MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        
		helper.setTo(user.getEmail());
		helper.setSubject(subject);
		helper.setText(content,true);
		helper.setFrom("non-reply@gmail.com");
		mailSender.send(mail);
		
		
	}
	
	

}
