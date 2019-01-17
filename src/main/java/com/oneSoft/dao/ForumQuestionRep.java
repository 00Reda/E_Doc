package com.oneSoft.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneSoft.entities.Question;

public interface ForumQuestionRep extends JpaRepository<Question, Long>{

}
