package com.pts.unige.Models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Document(collection = "users")
@AllArgsConstructor
public class User {
	
	@Getter
	private String name;
	
	@Id
	@Getter
	private String mobile;
	
	@Getter
	private String email;
	
	@Getter
	private List<Product> userProducts = new ArrayList<>();

}
