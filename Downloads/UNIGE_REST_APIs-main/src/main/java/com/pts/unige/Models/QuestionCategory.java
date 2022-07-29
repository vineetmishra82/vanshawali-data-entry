package com.pts.unige.Models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "questionCategories")
@AllArgsConstructor
public class QuestionCategory {
	
	@Id
	@Getter
	private String categoryId;
	
	@Getter
	private String name;
	
	@Getter
	@Setter
	private boolean isNext;
	
	@Getter
	@Setter
	private List<ProductFeedbackQuestion> feedbackQuestion;
}