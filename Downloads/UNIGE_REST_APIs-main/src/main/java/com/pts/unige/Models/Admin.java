package com.pts.unige.Models;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Document(collection = "admin")
@RequiredArgsConstructor
public class Admin {

	@Getter
	private String userId;
	
	
	@Getter
	private String password;
	
}
