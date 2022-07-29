package com.pts.unige.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Document(collection = "answerType")
@AllArgsConstructor
public class AnswerType {
	
	@Id
	@Getter
	@Setter
	private String answerType;

	

}
