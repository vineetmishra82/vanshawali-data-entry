package com.pts.unige.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pts.unige.Models.*;
import com.pts.unige.Service.ServiceData;

import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ControllerClass {

	@Autowired
	ServiceData service;
	
	
	public ControllerClass(ServiceData service) {
		this.service = service;
	
	}
	
	
	@GetMapping("/")
	public String getHome() {
		return "Its working !!";
	}
	

	@GetMapping("/adminLogin")
	public boolean adminLoginCheck(@RequestParam String userId,String password) 
	{
		
		return service.adminLogin(userId,password);
	}
	

	@GetMapping("/getAllUsers")
	public List<User> getUsers() 
	{
		
		return service.getAllUsers();
	}
	
	@GetMapping("/getUser")
	public User getUser(@RequestParam String mobile) 
	{
		
		return service.getUser(mobile);
	}
	

	@GetMapping("/userExists")
	public boolean userExists(@RequestParam String mobile) 
	{
		
		return service.userExists(mobile);
	}
	
	

	@PostMapping("/createUser")
	public boolean createUser(@RequestParam String name,String mobile,String email) 
	{
		
		return service.createUser(new User(name,mobile,email,new ArrayList<Product>()));
	}
	
	//Products CRUD
	

	@GetMapping("/getAllProducts")
	public List<Product> getProducts() 
	{
		log.warn("warn message");
		log.info("Getting products");
		return service.getAllProducts();
	}
	
	

	@PostMapping("/createProduct")
	public boolean createProduct(@RequestParam String name) 
	{
		
		return service.createProduct(new Product(name,new HashMap<String,String>(),
				new ArrayList<Survey>(),true));
	}
	

	@PostMapping("/updateProduct")
	public boolean updateProduct(@RequestParam String name,@RequestParam String newName) 
	{
		
		return service.updateProduct(name,newName);
	}
	

	@DeleteMapping("/deleteProduct")
	public boolean deleteProduct(@RequestParam String name) 
	{
		
		return service.deleteProduct(name);
	}
	
	//Features CRUD


	@GetMapping("/getFeatures")
	public List<String> getFeatures(@RequestParam String prodName) 
	{
		
		return service.getFeatures(prodName);
	}
	

	@PostMapping("/createProductFeature")
	public boolean createProductFeature(@RequestParam String prodName,String feature) 
	{
		
		return service.createProductFeature(prodName,feature);
	}
	


	@PostMapping("/updateProductFeature")
	public boolean updateProductFeature(@RequestParam String prodName,@RequestParam String feature, 
			@RequestParam String newFeature) 
	{
		
		return service.updateProductFeature(prodName,feature,newFeature);
	}
	

	@DeleteMapping("/deleteProductFeature")
	public boolean deleteProductFeature(@RequestParam String prodName,@RequestParam String feature) 
	{
		
		return service.deleteProductFeature(prodName,feature);
	}

	@GetMapping("/getAllCategories")
	public List<Survey> getAllCategories()
	{
		log.warn("warn message");
		log.info("Getting categories");
		return service.getAllCategories();
	}
	
	@PostMapping("/createNewCategory")
	public boolean createNewCategory(@RequestParam String id, String name)
	{
		return service.createNewCategory(id,name);
	}
	
	@PostMapping("/updateCategory")
	public boolean updateCategory(@RequestParam String oldId,
			String newId,String newName)
	{
		return service.updateCategory(oldId, newId, newName);
	}
	
	@DeleteMapping("/deleteCategory")
	public boolean deleteCategory(String id)
	{
		return service.deleteCategory(id);
	}

	//User-Product Interactions
	
	@PostMapping("/registerProduct")
	public boolean registerProduct(@RequestParam String userMobile,
			@RequestParam String productName,
			@RequestBody Map<String,String> features)
	{
		return service.registerProduct(userMobile, productName, features);
	}
	
	@GetMapping("/getUserProducts")
	public List<Product> getUserProduct(String userMobile)
	{
		return service.getUserProducts(userMobile);
		
	}
	
	@PostMapping("/addSurveyCategory")
	public boolean addSurveyCategory(@RequestParam String prodName,
			@RequestParam String surveyId)
			
	{
		return service.addSurveyCategory(prodName,surveyId);
	}
	
	@GetMapping("/getAllAnswerTypes")
	public List<AnswerType> getAllAnswerTypes()
	{
		return service.getAllAnswerType();
	}
	
	@PostMapping("/addAnswerType")
	public boolean setAnswerType(String answerType)
	{
		return service.setAnswerType(answerType);
	}
	
	@PostMapping("/addQuestionToSurvey")
	public boolean addQuestionToSurvey(@RequestParam String surveyId,@RequestParam String question,@RequestParam String questionType )
	{
		return service.addQuestionToSurvey(surveyId,question,questionType);
	}
	
	@PostMapping("/setSequence")
	public boolean setSequence(@RequestBody String[] sequence) {
		log.info("in set sequqnce");
		
		return service.setSequence(sequence);
	}
}

