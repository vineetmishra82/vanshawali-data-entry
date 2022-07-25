package com.pts.unige.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pts.unige.Models.QuestionCategory;

public interface QuestionCategoryRepo extends MongoRepository<QuestionCategory, String> {

}
