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
	private String answerType; //Slider or Descriptive


	@Override
	public boolean equals(Object obj) {
		
		ProductFeedbackQuestion pd = (ProductFeedbackQuestion) obj;
		
		
		return (this.question.equals(pd.getQuestion()) && this.answerType.equals(pd.getAnswerType()));
	}


	@Override
	public String toString() {
		return "ProductFeedbackQuestion [question=" + question + ", answer=" + answer + ", answerType=" + answerType
				+ "]";
	}
	
	
	
}
