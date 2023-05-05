package com.diana.backend.app.backendapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diana.backend.app.backendapp.models.entities.Product;
import com.diana.backend.app.backendapp.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{
  
  @Autowired
  private ProductRepository repository;

  @Override
  @Transactional(readOnly = true)
  public List<Product> findAll() {
    return (List<Product>)repository.findAll();
  }

  @Override
  public Optional<Product> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  @Transactional
  public Product save(Product product) {
    return repository.save(product);
  }

  @Override
  @Transactional
  public Optional<Product> update(Product product, Long id) {
    Optional<Product> o = this.findById(id);
    Product productOptional = null;
    if(o.isPresent()){
      Product productDb = o.orElseThrow();
      productDb.setDestination(product.getDestination());
      productDb.setPlan(product.getPlan());
      productDb.setStartDate(product.getStartDate());
      productDb.setEndDate(product.getEndDate());
      productOptional = this.save(productDb);
    } 
    return Optional.ofNullable(productOptional);
  }

  @Override
  @Transactional
  public void remove(Long id) {
    repository.deleteById(id);
  }
  
}
