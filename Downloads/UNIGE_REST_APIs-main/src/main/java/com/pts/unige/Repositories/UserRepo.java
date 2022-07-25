package com.pts.unige.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pts.unige.Models.User;

public interface UserRepo extends MongoRepository<User, String> {

}
