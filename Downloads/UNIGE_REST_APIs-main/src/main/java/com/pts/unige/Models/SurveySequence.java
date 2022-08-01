package com.pts.unige.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "surveySequence" )
@AllArgsConstructor
public class SurveySequence {
	
	@Id
	@Getter
	@Setter
	private int sequenceNo;
	
	@Getter
	@Setter
	private String surveyIdString;

}
