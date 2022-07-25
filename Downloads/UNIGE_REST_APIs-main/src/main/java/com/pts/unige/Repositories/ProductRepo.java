package com.pts.unige.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pts.unige.Models.Product;

public interface ProductRepo extends MongoRepository<Product, String> {

}
