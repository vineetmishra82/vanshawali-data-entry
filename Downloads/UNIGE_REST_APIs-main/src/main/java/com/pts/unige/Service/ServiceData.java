package com.pts.unige.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.UpdateResult;
import com.pts.unige.Models.*;
import com.pts.unige.Repositories.*;

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
	QuestionCategoryRepo categoriesRepo;
	
	@Autowired
	AnswerTypeRepo answerTypeRepo;
	
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
						new ArrayList<Survey>(),true));
				
				//Deactivating all other products of same category
				
				
				
				
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
		return categoriesRepo.findAll();
	}

	public boolean createNewCategory(String id, String name) {

		try {
			
			categoriesRepo.save(new Survey(id, name,false,false
					,new ArrayList<ProductFeedbackQuestion>()));
			return true;
		}catch(Exception e)
		{
			return false;
		}		
	
	}
	
	public boolean updateCategory(String oldId,String newId, String newName) {

		try {
			categoriesRepo.deleteById(oldId);
			categoriesRepo.save(new Survey(newId, newName,false,false
					,new ArrayList<ProductFeedbackQuestion>()));
			return true;
		}catch(Exception e)
		{
			return false;
		}		
	
	}
	
	public boolean deleteCategory(String id) {

		try {
			categoriesRepo.deleteById(id);
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
	
	public Survey getQuestionCategory(String questionId)
	{
		for(Survey questionCat : categoriesRepo.findAll())
		{
			if(questionCat.getCategoryId().equals(questionId))
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
			
			product.setFeatures(features);
			
			user.getUserProducts().add(product);
			
			//Setting product as active
			product.setActive(true);
			
			//setting all other user products of same category as false
			
			for(Product otherProduct : user.getUserProducts())
			{
				if(!otherProduct.equals(product) && otherProduct.getProductName()
						.equals(product.getProductName()))
				{
					otherProduct.setActive(false);
				}
			}
			
			userRepo.save(user);
			return true;
		}catch(Exception e)
		{
			return false;
		}	
		
	}
	
	public List<Product> getUserProducts(String userMobile)
	{
		User user = getUser(userMobile);
		
		return user.getUserProducts();
	}

	public boolean addSurveyCategory(String prodName, String surveyId) {
		
		try {
			Product product = getProduct(prodName);
			Survey survey = getQuestionCategory(surveyId);
			
			product.getSurveys().add(survey);
			productRepo.save(product);
			
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
			return false;
		}		
		
	}
	
	
	public List<AnswerType> getAllAnswerType()
	{
		return answerTypeRepo.findAll();
	}
	
	public boolean setAnswerType(String answerType)
	{
		try {
			
			answerTypeRepo.save(new AnswerType(answerType));
			return true;
		}catch(Exception e)
		{
			return false;
		}
	}

	public boolean addQuestionToSurvey(String surveyId, String question, String questionType) {
		
		try {
			Survey survey = getQuestionCategory(surveyId);
			
			survey.getFeedbackQuestion().add(new ProductFeedbackQuestion(question,"",questionType));
			categoriesRepo.save(survey);
			return true;
		}catch(Exception e)
		{
			return false;
		}
		

	}
}
