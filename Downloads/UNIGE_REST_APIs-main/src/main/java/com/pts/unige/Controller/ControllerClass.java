package com.pts.unige.Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.json.JsonMode;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pts.unige.Models.*;
import com.pts.unige.Service.ServiceData;

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
	public boolean createProduct(@RequestParam String name,@RequestParam int days) 
	{
		Map<String, String> features = new HashMap<>();
		
		features.put("Purchase Date", "");
		features.put("Brand", "");
		features.put("Model", "");
		features.put("Purchase Type", "");
		features.put("Price(in $ approx)", "");
		
		//With no of days in trhe repositrorty
		
		log.info("checking for git");
		
		return service.createProduct(new Product(name,features,
				days,new ArrayList<Survey>(), true,null));
	}
	

	@PostMapping("/updateProduct")
	public boolean updateProduct(@RequestParam String name,@RequestParam String newName,
			@RequestParam int days) 
	{
		//This is for service update
		
		return service.updateProduct(name,newName,days);
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
	
	@PostMapping("/createNewSurvey")
	public boolean createNewSurvey(@RequestParam String id, @RequestParam String name,
			@RequestParam boolean isDefectSurvey,@RequestBody String body)
	{
		JSONObject obj= new JSONObject(body);
		String thankYouText = obj.getString("surveyThanksMessage");
		return service.createNewSurvey(id,name,isDefectSurvey,thankYouText);
	}
	
	@PostMapping("/updateSurvey")
	public boolean updateSurvey(@RequestParam String oldId,
			@RequestParam String newId,@RequestParam String newName,
			@RequestParam String isDefectSurvey,
			@RequestBody String body)
	{
		
		boolean isDefect = isDefectSurvey.equals("Yes") ? true : false;
		
		return service.updateSurvey(oldId, newId, newName,isDefect,body);
	}
	
	@DeleteMapping("/deleteSurvey")
	public boolean deleteSurvey(String id)
	{
		return service.deleteSurvey(id);
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
	
	@PostMapping("/addSurvey")
	public boolean addSurveySurvey(@RequestParam String prodName,
			@RequestParam String surveyId)
			
	{
		return service.addSurveySurvey(prodName,surveyId);
	}
	
		
	@PostMapping("/addQuestionToSurvey")
	public boolean addQuestionToSurvey(@RequestParam String surveyId,@RequestParam String question
			,@RequestParam String questionType,@RequestBody String body )
	{
		
		return service.addQuestionToSurvey(surveyId,question,questionType,body);
	}
	
	@PostMapping("/setSequence")
	public boolean setSequence(@RequestBody String sequence) {
		log.info("in set sequqnce");
		log.info("Seq is "+sequence.toString());
		
		return service.setSequence(sequence);
		
	}
	
	@GetMapping("/getQuestionsListFromSurveyId")
	public List<ProductFeedbackQuestion> getQuestionsListFromSurveyId(String surveyId)
	{
		return service.getQuestionsListFromSurveyId(surveyId);
	}
	
	@PostMapping("/editQuestionListFromSurveyId")
	public boolean editQuestionListFromSurveyId(@RequestParam String surveyId
			,@RequestBody String body)
	{
		
			
		return service.editQuestionListFromSurveyId(surveyId,body);
	}
	
	@DeleteMapping("/deleteQuestionListFromSurveyId")
	public boolean deleteQuestionListFromSurveyId(@RequestParam String surveyId,
			@RequestParam String question,
			@RequestParam String answerType)
	{

		
		return service.deleteQuestionListFromSurveyId(surveyId,question,answerType);
	}
	
	//AnswerType CRUD
	
	@GetMapping("/getAllAnswerTypes")
	public List<AnswerType> getAllAnswerTypes()
	{
		return service.getAllAnswerType();
	}
	
	@PostMapping("/addAnswerType")
	public boolean setAnswerType(@RequestParam String answerType,@RequestBody String ratingValues)
	{
		return service.setAnswerType(answerType,ratingValues);
	}
	
	@PostMapping("/editAnswerType")
	public boolean editAnswerType(@RequestParam String oldAnswerType,@RequestParam String newAnswerType
			,@RequestBody String[] ratingValues)
	{
		return service.editAnswerType(oldAnswerType,newAnswerType,ratingValues);
	}
	
	@DeleteMapping("/deleteAnswerType")
	public boolean deleteAnswerType(String answerType)
	{
		
		return service.deleteAnswerType(answerType);
	}
	
	@PostMapping("/copyAnswerFromOneSurveyToAnother")
	public boolean copyAnswerFromOneSurveyToAnother(@RequestParam String newSurvey,
			@RequestParam String oldSurvey)
	{
		return service.copyAnswerFromOneSurveyToAnother(newSurvey,oldSurvey);
	}
	
	@GetMapping("/getNonDefectCategories")
	public List<Survey> getNonDefectCategories(){
		
		return service.getNonDefectCategories();
	}
	 
	@GetMapping("/getRatingsArray")
	 public List<AnswerType> getRatingsArray()
	 {
		 return service.getRatingsArray();
	 }
	
	@PostMapping("/submitFeedback")
	public boolean submitFeedback(@RequestParam String mobile,@RequestParam String myProductSelected,
			@RequestBody String surveyJson) {
	
		log.info(surveyJson);
		Survey survey;
	
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	   
	    try {
	        survey = mapper.readValue(surveyJson, Survey.class);
	}catch(Exception e)
	    {
		return false;
	    }
		return service.submitFeedback(mobile,myProductSelected,survey);
	}
	
	@GetMapping("/generateAndGetDefectSurveys")
	public List<Survey> getDefectSurveys(@RequestParam String userMobile,@RequestParam String product)
	{
		return service.generateAndGetDefectSurveys(userMobile,product);
	}
	
	//Special requests URLs
	
	@DeleteMapping("/deleteUserProduct")
	public boolean deleteUserProduct(@RequestParam String userMobile,@RequestParam
		String userProduct) {
		return service.deleteUserProduct(userMobile,userProduct);
	}
	


}

