package com.pts.unige.Models;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "products")
@AllArgsConstructor
public class Product {
	
	@Id
	@Getter
	@Setter
	private String productName;
	
	@Getter
	@Setter
	private Map<String,String> features;
	
	
	@Getter
	@Setter
	private List<ProductFeedbackQuestion> feedback;
	
	@Getter
	@Setter
	private boolean isActive;
	
}
