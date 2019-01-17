package com.oneSoft;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.oneSoft.dao.IEspacePublicRepository;
import com.oneSoft.entities.Document;

@SpringBootApplication
public class EDocApplication {

	public static SimpleDateFormat docDataFormat=new SimpleDateFormat("dd-M-yyyy");
	public static SimpleDateFormat userDataFormat=new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

	public static void main(String[] args) {
		
		ApplicationContext tx=SpringApplication.run(EDocApplication.class, args);
		IEspacePublicRepository espacePublicRep = tx.getBean(IEspacePublicRepository.class);
		
		//Optional<Document> d=espacePublicRep.findById(1L);
		
		
		 
		String password = "1234";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		System.err.println(hashedPassword);
		
		
	}

}

