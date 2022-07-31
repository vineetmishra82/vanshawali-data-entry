package com.pts.unige.Models;



import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "ProductFeedbackQuestions")
@AllArgsConstructor
public class ProductFeedbackQuestion  {

	@Getter
	@Setter
	private String question;
	
	@Getter
	@Setter
	private String answer;
	
	
	@Getter
	@Setter
	private String answerType;//Slider or Descriptive
	
	
	
}
