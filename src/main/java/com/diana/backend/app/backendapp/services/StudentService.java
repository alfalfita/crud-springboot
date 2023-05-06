package com.diana.backend.app.backendapp.services;

import java.util.List;

import com.diana.backend.app.backendapp.models.entities.Student;

public interface StudentService {
  
  List<Student> findAll();

  Student save (Student user);
}
