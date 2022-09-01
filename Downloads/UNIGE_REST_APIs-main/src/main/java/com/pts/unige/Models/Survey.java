package com.pts.unige.Models;


import java.util.List;
import java.util.Date;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "surveys")
@ToString
@AllArgsConstructor
public class Survey {
	
	@Id
	@Getter
	private String surveyId;
	
	@Getter
	private String name;
	
	@Getter
	@Setter
	private boolean isNext;
	
	@Getter
	@Setter
	private boolean isComplete;
	
	@Getter
	private boolean isDefectSurvey;
	
	@Getter
	@Setter
	private boolean isDeleteAble;
	
	@Getter
	@Setter
	private List<ProductFeedbackQuestion> feedbackQuestion;

	@Getter
	@Setter
	private Date activationDate;
	
}