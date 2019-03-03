package com.CTi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CTi.entities.Question;

public interface ForumQuestionRep extends JpaRepository<Question, Long>{

}
