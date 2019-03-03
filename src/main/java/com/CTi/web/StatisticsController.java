package com.CTi.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CTi.DocStatistics;
import com.CTi.repository.DocumentRep;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class StatisticsController {
	
	 @Autowired
		private DocumentRep docRep ;
	    
	 @Autowired
	    private ObjectMapper objectMapper ;
	
	 @RequestMapping(value="/dashboard/admin/statisticsY", produces=MediaType.APPLICATION_JSON_VALUE)
	  public String StatisticsDocsPerYear () {
		  List<DocStatistics> list=docRep.statistics_docPerYear();
		  
		  
		  try {
			return this.objectMapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{}";  
		}
	  }
	 
	 @RequestMapping(value="/dashboard/admin/statisticsMY", produces=MediaType.APPLICATION_JSON_VALUE)
	  public String StatisticsDocsPerMonthYear () {
		  List<DocStatistics> list=docRep.statistics_docPerMonthYear();
		  
		  
		  try {
			return this.objectMapper.writeValueAsString(list);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "{}";  
			}
	  }

}
