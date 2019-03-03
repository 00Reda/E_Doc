package com.CTi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DocStatistics {
	
	private long total ; 
	private String date;
	
	public DocStatistics(long total , int m , int y) {
		this.total=total;
		date=m+"/"+y;
	}
	
	public DocStatistics(long total , int y) {
		this.total=total;
		date=y+"";
	}

}
