package com.pts.unige.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pts.unige.Models.ProductFeedbackQuestion;

public interface ProductFeedbackQuestionRepo extends MongoRepository<ProductFeedbackQuestion, String> {

}
