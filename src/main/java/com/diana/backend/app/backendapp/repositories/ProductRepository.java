package com.diana.backend.app.backendapp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.diana.backend.app.backendapp.models.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{

  
  
}
