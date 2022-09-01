package com.pts.unige.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pts.unige.Models.*;
import com.pts.unige.Repositories.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.mongodb.core.MongoTemplate;

@Service
@Slf4j
public class ServiceData {
	
	 private static Logger logger = LoggerFactory.getLogger(ServiceData.class);
	@Autowired
	AdminRepo adminRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	ProductRepo productRepo;

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	SurveyRepo surveysRepo;
	
	@Autowired
	AnswerTypeRepo answerTypeRepo;
	
	@Autowired
	SurveySequenceRepo surveySequenceRepo;
	
	boolean opsResult;

	public boolean adminLogin(String userId, String password) {

		List<Admin> admins = adminRepo.findAll();

		for (Admin admin : admins) {

			if (admin.getUserId().equals(userId) && admin.getPassword().equals(password))

				return true;
		}

		return false;
	}

	public boolean createUser(User user) {
		try {
			userRepo.save(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean createProduct(Product product) {
		try {
			productRepo.save(product);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}

	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return productRepo.findAll();
	}

	public boolean createProductFeature(String prodName, String feature) {

		List<Product> products = productRepo.findAll();

		for (Product product : products) {

			if (product.getProductName().equals(prodName)) {
				try {
					product.getFeatures().put(feature, "");
					productRepo.saveAll(products);
					return true;
				} catch (Exception e) {
					return false;
				}
			}
		}

		return false;
	}

	public boolean userExists(String mobile) {

		List<User> users = userRepo.findAll();

		for (User user : users) {

			if (user.getMobile().equals(mobile)) {
				return true;
			}

		}

		return false;
	}

	public List<String> getFeatures(String prodName) {
		
		List<Product> products = productRepo.findAll();
		logger.info("ProdName is "+prodName);
		List<String> onlyFeatures = new ArrayList<String>();

		for (Product product : products) {
			logger.info("Prod is "+product.getProductName());
			if (product.getProductName().equals(prodName)) {
				
				onlyFeatures = getOnlyFeaturesName(product);
				logger.info("list size is "+onlyFeatures.size());
				}
				

				
			}

		return onlyFeatures;
		
	}

	public boolean updateProduct(String name, String newName) {

		Optional<Product> product = productRepo.findById(name);
		

		product.ifPresentOrElse((value) -> {
			
			try {
				productRepo.deleteById(name);
				productRepo.save(new Product(newName,new HashMap<String,String>(),
						new ArrayList<Survey>(),true,null));
				
				//Deactivating all other products of same survey
				
				
				
				
				opsResult = true;
			} catch (Exception e) {
				opsResult = false;
			}
		}, () -> {
					opsResult = false;
				});

		return opsResult;

	}

	private List<String> getOnlyFeaturesName(Product product) {

		List<String> onlyFeatures = new ArrayList<String>();
		logger.info("here");
		logger.info("Map size is "+product.getFeatures().entrySet().size());
		
		
		for (Map.Entry<String, String> keys : product.getFeatures().entrySet()) {
			
			onlyFeatures.add(keys.getKey());
			
		}
		
		return onlyFeatures;
		
	}

	public boolean deleteProduct(String name) {

		Optional<Product> product = productRepo.findById(name);
		
		product.ifPresentOrElse((value) -> {
			try {
				productRepo.deleteById(name);
				
				opsResult = true;
			} catch (Exception e) {
				opsResult = false;
			}
		}

				, () -> {
					opsResult = false;
				});

		return opsResult;

	}

	public boolean updateProductFeature(String prodName, String featureName, String newFeature) {
		List<Product> products = productRepo.findAll();

		for (Product product : products) {

			if (product.getProductName().equals(prodName)) {
				
				Map<String,String> features = product.getFeatures();
				
				String featureValue = features.get(featureName);
				features.remove(featureName);
				features.put(newFeature, featureValue);
				product.setFeatures(features);
				
				productRepo.saveAll(products);
				
				return true;
				}
			}
		

		return false;
	}

	public boolean deleteProductFeature(String prodName, String featureName) {
		List<Product> products = productRepo.findAll();

	
		
		
		for (Product product : products) {

			if (product.getProductName().equals(prodName)) {
				
					if (product.getFeatures().containsKey(featureName)) {
						Map<String,String> features = product.getFeatures();
						features.remove(featureName);

						product.setFeatures(features);

						productRepo.saveAll(products);
						return true;
					}
				}
			}
		

		return false;
	}

	public List<Survey> getAllCategories() {
		// TODO Auto-generated method stub
		return surveysRepo.findAll();
	}

	public boolean createNewSurvey(String id, String name,boolean isDefectSurvey) {

		try {
			
			boolean isDeletaAble = true;
			
			if(id.contains("QS1") || id.contains("QS2") || 
					id.contains("Defect Report") || id.contains("Replacement")
					|| id.contains("Complaint" ))
			{
				isDeletaAble = false;
			}
			
			surveysRepo.save(new Survey(id, name,false,false,isDefectSurvey,isDeletaAble
					,new ArrayList<ProductFeedbackQuestion>(),new Date()));
			return true;
		}catch(Exception e)
		{
			return false;
		}		
	
	}
	
	public boolean updateSurvey(String oldId,String newId, String newName) {

		try {
			
			boolean isDefect = getQuestionSurvey(oldId).isDefectSurvey();
			boolean isDeletable = getQuestionSurvey(oldId).isDeleteAble();
			surveysRepo.deleteById(oldId);
			surveysRepo.save(new Survey(newId, newName,isDefect,false,false,isDeletable
					,new ArrayList<ProductFeedbackQuestion>(),null));
			return true;
		}catch(Exception e)
		{
			return false;
		}		
	
	}
	
	public boolean deleteSurvey(String id) {

		try {
			surveysRepo.deleteById(id);
			return true;
		}catch(Exception e)
		{
			return false;
		}		
	
	}

	public User getUser(String mobile) {
		log.info("User mobile is "+mobile);
		for(User user : userRepo.findAll())
			
		{
			log.info("Abhi mobile is "+user.getMobile());
			if(user.getMobile().equals(mobile))
			{
				return user;
			}
				
		}	
		
		return null;
	}
	
	public Product getProduct(String prodName) {
		
		for(Product product : productRepo.findAll())
			
		{
			if(product.getProductName().equals(prodName))
			{
				return product;
			}
				
		}	
		
		return null;
	}
	
	public Survey getQuestionSurvey(String questionId)
	{
		for(Survey questionCat : surveysRepo.findAll())
		{
			if(questionCat.getSurveyId().equals(questionId))
			{
				return questionCat;
			}
		}
		
		return null;
	}
	

	public boolean registerProduct(String userMobile, String productName, Map<String, String> features) {
		
		try {
			User user = getUser(userMobile);
			Product product = getProduct(productName);	
			
			product.setRegistrationDate(new Date());
			product.setFeatures(features);
			
			user.getUserProducts().add(product);
			
			//Setting product as active
			product.setActive(true);
			
			//setting all other user products of same survey as false
			
			for(Product otherProduct : user.getUserProducts())
			{
				if(!otherProduct.equals(product) && otherProduct.getProductName()
						.equals(product.getProductName()))
				{
					otherProduct.setActive(false);
				}
			}
			
			user = setNextSurveyForFeedback(user,product);
			
			log.info(userMobile.toString());
			userRepo.save(user);
			
			return true;
		}catch(Exception e)
		{
			log.info(e.getMessage());
			return false;
		}	
		
	}
	
	private User setNextSurveyForFeedback(User user, Product product) {
		
		int prodIndex = user.getUserProducts().indexOf(product); 
		
		List<Survey> surveys = user.getUserProducts().get(prodIndex).getSurveys();
		
		Date regDate = user.getUserProducts().get(prodIndex).getRegistrationDate();
		
		List<SurveySequence> surveySeq = surveySequenceRepo.findAll();
		
		for (Survey survey : surveys) {
			
			for (SurveySequence surveySequence : surveySeq) {
				
				if(survey.getSurveyId().equals(surveySequence.getSurveyId()))
				{
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(regDate);
					
					
					
					calendar.add(Calendar.DATE, (int)surveySequence.getDaysToActivate());
					
										
					survey.setActivationDate(calendar.getTime());
					
					log.info("Survey - "+survey.getSurveyId());
					log.info("compare to value - "+String.valueOf(regDate.compareTo(survey.getActivationDate())));
				
					if(regDate.compareTo(survey.getActivationDate())==0)
					{
						log.info("Survey being set true is "+survey.getSurveyId());
						survey.setNext(true);
					}
				}
			}
			
		}
		
		return user;
	}

	public List<Product> getUserProducts(String userMobile)
	{
		User user = getUser(userMobile);
		
		return user.getUserProducts();
	}

	public boolean addSurveySurvey(String prodName, String surveyId) {
		
		try {
			Product product = getProduct(prodName);
			Survey survey = getQuestionSurvey(surveyId);
			
			log.info("Product is "+product.toString());
			log.info("Survey is "+surveyId.toString());
			
			boolean isfound = false;
			for (Survey surveyInProduct : product.getSurveys()) {
				
				if(surveyInProduct.getSurveyId().equals(survey.getSurveyId()))
				{
					isfound=true;
					break;
				}
				
			}
			
			if(!isfound)
			{
				product.getSurveys().add(survey);
				productRepo.save(product);
			}
			
			
			//Updating in all existing user products
			
			for(User user : userRepo.findAll())
			{
				if(user.getUserProducts().contains(product))
				{
					int index = user.getUserProducts().indexOf(product);
					log.info("Index is "+index);
					user.getUserProducts().set(index, product);
					log.info(user.getUserProducts().get(index).toString());
					userRepo.save(user);
					
				}
			}		
			
			
			
			return true;
		}catch(Exception e)
		{
			log.info(e.getMessage());
			return false;
		}		
		
	}
	
	
	public List<AnswerType> getAllAnswerType()
	{
		return answerTypeRepo.findAll();
	}
	
	public boolean setAnswerType(String answerType, String ratingValues)
	{
		try {
			
			log.info("rating values is "+ratingValues.length());
			if(ratingValues.contains("ratingValues"))
			{
				JSONObject seqObject = new JSONObject(ratingValues);
				
				JSONArray seqArray = seqObject.getJSONArray("ratingValues");
				log.info(seqArray.toString());
				
				String[] arr = new String[seqArray.length()];
				
				for(int i=0;i<seqArray.length();i++)
				{
					arr[i] = seqArray.get(i).toString();
				}
				
				answerTypeRepo.save(new AnswerType(answerType,arr));
			}
			else {
				answerTypeRepo.save(new AnswerType(answerType,null));
			}
			
			return true;
		}catch(Exception e)
		{
			log.info(e.getMessage());
			return false;
		}
	}

	public boolean addQuestionToSurvey(String surveyId, String question, String questionType, String body) {
		
		try {
			//Decoding body
			
			JSONObject bodyObject = new JSONObject(body);
			
			String mainScreenTitle = bodyObject.getString("mainScreentitle");
			String titleLine = bodyObject.getString("titleLine");
			String questionTitle = bodyObject.getString("questionTitle"); 
			
			
			Survey survey = getQuestionSurvey(surveyId);
			
			survey.getFeedbackQuestion().add(new ProductFeedbackQuestion(mainScreenTitle,titleLine,
					questionTitle,question,"",questionType));
			surveysRepo.save(survey);
			return true;
		}catch(Exception e)
		{
			return false;
		}
		

	}

	public boolean setSequence(String sequence) {
		
		
		try {
			surveySequenceRepo.deleteAll();
		JSONObject seqObject = new JSONObject(sequence);
		
		JSONArray seqArray = seqObject.getJSONArray("sequenceArray");
		
		
		log.info(seqArray.toString());
		
			for (int i=0;i<seqArray.length();i++) {
				
				surveySequenceRepo.save(new SurveySequence(Double.parseDouble((String) seqArray.getJSONObject(i).get("days")),
						seqArray.getJSONObject(i).get("surveyId").toString()));
				
		
			
		}
			return true;
		}
			catch(Exception e)
		{
			log.info(e.getMessage());
			return false;
		}
		
		
	
	}

	public List<ProductFeedbackQuestion> getQuestionsListFromSurveyId(String surveyId) {
		
		Survey survey = getQuestionSurvey(surveyId);
		
		
		return survey.getFeedbackQuestion();
	}

	
	public boolean editQuestionListFromSurveyId(String surveyId,
			ProductFeedbackQuestion oldPfq, ProductFeedbackQuestion newPfq) {
		
		try {
			Survey survey = getQuestionSurvey(surveyId);
			
			int index = -1;
			for (ProductFeedbackQuestion prodFeed : survey.getFeedbackQuestion()) {
				
				log.info("prodFeed - "+prodFeed.toString());
				log.info("oldPfq - "+oldPfq.toString());
				
				if(prodFeed.equals(oldPfq))
				{
					index = survey.getFeedbackQuestion().indexOf(oldPfq);
					break;
				}
				
			}
			
			if(index>-1)
			{
				survey.getFeedbackQuestion().remove(index);
				survey.getFeedbackQuestion().add(newPfq);				
			}
			else {
				return false;
			}
			
			surveysRepo.save(survey);
			return true;
			
		}catch(Exception e)
		{		
		return false;
		}
		
	}

	public boolean deleteQuestionListFromSurveyId(String surveyId, ProductFeedbackQuestion newPfq) {
		try {
			Survey survey = getQuestionSurvey(surveyId);
			int index =-1;
			if(survey.getFeedbackQuestion().contains(newPfq))
			{
				index = survey.getFeedbackQuestion().indexOf(newPfq);
				survey.getFeedbackQuestion().remove(index);
						
			}
			
			if(index>-1)
			{
				surveysRepo.save(survey);
				return true;
			}else {
				return false;
			}
			
			
			
		}catch(Exception e)
		{		
		return false;
		}
	}

	public boolean editAnswerType(String oldAnswerType, String newAnswerType, String[] ratingValues) {
	
		try {
			answerTypeRepo.deleteById(oldAnswerType);
			answerTypeRepo.save(new AnswerType(newAnswerType,ratingValues));
			
			return true;
			
		}catch(Exception e)
		{	
		return false;
		}
	}

	public boolean deleteAnswerType(String answerType) {
		try {
			answerTypeRepo.deleteById(answerType);
			
			
			return true;
			
		}catch(Exception e)
		{	
		return false;
		}
	}

	public boolean copyAnswerFromOneSurveyToAnother(String newSurvey, String oldSurvey) {
		
		try {
			Survey newSurveyName = getQuestionSurvey(newSurvey);
			Survey oldSurveyName = getQuestionSurvey(oldSurvey);
			
			newSurveyName.setFeedbackQuestion(oldSurveyName.getFeedbackQuestion());
			
			surveysRepo.save(newSurveyName);
			
			return true;
		}catch(Exception e)
		{
			log.info(e.getMessage());
			return false;
		}	
		
	}

	public List<Survey> getNonDefectCategories() {
		List<Survey> filteredSurveys = surveysRepo.findAll()
										.stream()
										.filter(value ->
										!value.isDefectSurvey()
												)
										.collect(Collectors.toList());	
							
				
		return filteredSurveys;
	}

	public List<AnswerType> getRatingsArray() {
		
		
		return answerTypeRepo.findAll();
	}

	public boolean submitFeedback(String mobile, String myProductSelected, Survey survey) {
		
		try {
			User user = getUser(mobile);
			
			int index = -1;
			
			for (Product product : user.getUserProducts()) {
				
				if(product.getProductName().equals(myProductSelected))
				{
					index = user.getUserProducts().indexOf(product);
					break;
				}
				
			}
			
			List<Survey> surveys = user.getUserProducts().get(index).getSurveys();
			
			int index1 = -1;
			for (Survey survey1 : surveys) {
				
				if(survey1.getSurveyId().equals(survey.getSurveyId()))
				{
					index1 = surveys.indexOf(survey1);
					break;
				}
				
			}
			
			surveys.remove(index1);
			survey.setComplete(true);
			survey.setNext(false);
			
			surveys.add(index1, survey);
			
			
			
			//setting next survey to be true
			
			if(survey.getSurveyId().equals("QS1"))
			{
				index1 = -1;
				for (Survey survey1 : surveys) {
					
					if(survey1.getSurveyId().equals("QS2"))
					{
						index1 = surveys.indexOf(survey1);
						break;
					}
					
				}
				
				surveys.get(index1).setNext(true);
			}
			
			user.getUserProducts().get(index).setSurveys(surveys);
			
			userRepo.save(user);
			return true;
			
			
		}
		catch(Exception e)
		{
			log.info(e.getLocalizedMessage());
			return false;
		}
		
		
		
	}

	public List<Survey> getDefectSurveys() {
		
		List<Survey> surveys = surveysRepo.findAll();
		List<Survey> defectSurveys = new ArrayList<>();
		
		for (Survey survey : surveys) {
			
			if(survey.isDefectSurvey())
			{
				defectSurveys.add(survey);
			}
			
		}
		
		return defectSurveys;
	}
	
}

	
