package com.pts.unige.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pts.unige.Models.AnswerType;

public interface AnswerTypeRepo extends MongoRepository<AnswerType, String> {

}
