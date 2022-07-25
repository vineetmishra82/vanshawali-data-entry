package com.pts.unige.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pts.unige.Models.Admin;



@Repository
public interface AdminRepo extends MongoRepository<Admin, String> {
	

}
