package com.diana.backend.app.backendapp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.diana.backend.app.backendapp.models.entities.Student;
import com.diana.backend.app.backendapp.services.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

public class StudentController {
  
  @Autowired
  private StudentService studentService;

  @GetMapping("/listAll")
  public List<Student> list(){
    return studentService.findAll();
  }

  @PostMapping("/add")
  public ResponseEntity<?> create(@Valid @RequestBody Student user, BindingResult result){
    if(result.hasErrors()){
      return validation(result);
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(studentService.save(user));
  }

  private ResponseEntity<?> validation(BindingResult result) {
    Map<String, String> errors = new HashMap<>();
    result.getFieldErrors().forEach(err ->{
      errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
    });
    return ResponseEntity.badRequest().body(errors);
  }

}
