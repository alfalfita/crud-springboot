package com.diana.backend.app.backendapp.services;

import java.util.List;
import java.util.Optional;

import com.diana.backend.app.backendapp.models.entities.Product;

public interface ProductService {
  
  List<Product> findAll();

  Optional <Product> findById(Long id);

  Product save (Product product);

  Optional<Product> update (Product product, Long id);

  void remove(Long id);
  
}
