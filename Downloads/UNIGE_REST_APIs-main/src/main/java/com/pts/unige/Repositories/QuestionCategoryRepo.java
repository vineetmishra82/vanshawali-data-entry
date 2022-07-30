package com.pts.unige.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pts.unige.Models.Survey;

public interface QuestionCategoryRepo extends MongoRepository<Survey, String> {

}
