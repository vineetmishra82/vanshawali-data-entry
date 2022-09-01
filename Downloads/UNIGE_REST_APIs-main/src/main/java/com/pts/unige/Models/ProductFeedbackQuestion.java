package com.pts.unige.Models;



import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "ProductFeedbackQuestions")
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductFeedbackQuestion  {
	
	
	@Getter
	@Setter
	private String mainScreentitle;
	
	@Getter
	@Setter
	private String titleLine;
	
	@Getter
	@Setter
	private String questionTitle;
	
	@Getter
	@Setter
	private String question;
	
	@Getter
	@Setter
	private String answer;
		
	@Getter
	@Setter
	private String answerType; //Slider or Descriptive
	
	
}
