package com.diana.backend.app.backendapp.controllers;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.diana.backend.app.backendapp.models.entities.Product;
import com.diana.backend.app.backendapp.models.entities.Student;
import com.diana.backend.app.backendapp.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/course")
@CrossOrigin(originPatterns = "*")
public class ProductController {
  
  @Autowired
  private ProductService service;

  @GetMapping("/listAll")
  public List<Product> list(){
    return service.findAll();
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<?> show(@PathVariable(name="id") Long idProduct){
    Optional<Product> productOptional = service.findById(idProduct);

    if(productOptional.isPresent()){
      return ResponseEntity.ok(productOptional.orElseThrow());
    }
    return ResponseEntity.notFound().build();
  }


  @PostMapping("/add")
  public ResponseEntity<?> create(@Valid @RequestBody Product product, BindingResult result){
    if(result.hasErrors()){
      return validation(result);
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(service.save(product));
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<?> update(@Valid @RequestBody Product product, BindingResult result, @PathVariable Long id){
    if(result.hasErrors()){
      return validation(result);
    }

    Optional<Product> o = service.update(product, id);
    if(o.isPresent()){
      return ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow());
    }
    return ResponseEntity.notFound().build();
  }

  @PutMapping("/{id}/addStudent")
  public ResponseEntity<?> addStudent(@RequestBody Student student,  @PathVariable Long id){
    Optional<Product> o = service.findById(id);
    if(o.isPresent()){
      Product course = o.get();
      course.addStudent(student);
      return ResponseEntity.status(HttpStatus.CREATED).body(service.save(course));
    }
    return ResponseEntity.notFound().build();
  }


  @PutMapping("/{id}/removeStudent")
  public ResponseEntity<?> removeStudent(@RequestBody Student student,  @PathVariable Long id){
    Optional<Product> o = service.findById(id);
    if(o.isPresent()){
      Product course = o.get();
      course.removeStudent(student);
      return ResponseEntity.status(HttpStatus.CREATED).body(service.save(course));
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id){
    Optional<Product> o = service.findById(id);
    if(o.isPresent()){
      service.remove(id);
      return ResponseEntity.noContent().build(); // 204
    }
    return ResponseEntity.notFound().build();
  }

  private ResponseEntity<?> validation(BindingResult result) {
    Map<String, String> errors = new HashMap<>();
    result.getFieldErrors().forEach(err ->{
      errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
    });
    return ResponseEntity.badRequest().body(errors);
  }
}
