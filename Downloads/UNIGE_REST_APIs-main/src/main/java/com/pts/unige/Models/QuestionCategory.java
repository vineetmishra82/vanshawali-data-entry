package com.pts.unige.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Document(collection = "questionCategories")
@AllArgsConstructor
public class QuestionCategory {
	
	@Id
	@Getter
	private String categoryId;
	
	@Getter
	private String name;
	
}