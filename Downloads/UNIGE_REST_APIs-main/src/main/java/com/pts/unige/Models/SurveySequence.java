package com.pts.unige.Models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "surveySequence" )
@ToString
@AllArgsConstructor
public class SurveySequence {
	
	
	@Getter
	@Setter
	private double daysToActivate;
	
	@Id
	@Getter
	@Setter
	private String surveyId;

}
