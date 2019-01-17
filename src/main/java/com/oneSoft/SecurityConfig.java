package com.oneSoft;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource dataSource;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		/*
		auth.inMemoryAuthentication().withUser("admin@gmail.com").password("{noop}1234").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("rida@gmail.com").password("{noop}1234").roles("ETUDIANT");
		auth.inMemoryAuthentication().withUser("brahima@gmail.com").password("{noop}1234").roles("PROF");
		*/
		
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery("select id_user as principal , password as credentials , active from utilisateur where email =?")
		.authoritiesByUsernameQuery("select username as principal , role as role from users_roles where username=? ")
		.rolePrefix("ROLE_").passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.formLogin().loginPage("/login").defaultSuccessUrl("/dashboard");
		http.authorizeRequests().antMatchers("/dashboard/prof/**").hasRole("PROF");
		http.authorizeRequests().antMatchers("/dashboard/etudiant").hasRole("ETUDIANT");
		http.authorizeRequests().antMatchers("/users/prof/**").hasRole("PROF");
		http.authorizeRequests().antMatchers("/docs/prof/**").hasRole("PROF");
		http.authorizeRequests().antMatchers("/dashboard/admin").hasRole("ADMIN");
		http.authorizeRequests().antMatchers("/com/**").hasAnyRole("ADMIN","ETUDIANT","PROF");
		http.authorizeRequests().antMatchers("/dashboard/*/calendar").hasAnyRole("ADMIN","ETUDIANT","PROF");
		http.authorizeRequests().antMatchers("/bookmark/**").hasAnyRole("ADMIN","ETUDIANT","PROF");
		http.exceptionHandling().accessDeniedPage("/403");
	
	}
	
	public static String crypter(String password) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}
	
	public static Boolean verifyPass(String oldpass,String userpass) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.matches(oldpass, userpass);
	}
     //AIzaSyDDkiliNkCUdFLUa4xDj2S8PM1JkXDWwCE
	// client id : 794933248634-qau2th88a58hmhuatqvuu7fhgsvb9mrh.apps.googleusercontent.com
}
