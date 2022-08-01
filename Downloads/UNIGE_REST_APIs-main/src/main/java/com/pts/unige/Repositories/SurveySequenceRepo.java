package com.pts.unige.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pts.unige.Models.SurveySequence;

public interface SurveySequenceRepo extends MongoRepository<SurveySequence, Integer> {

	
	
}
