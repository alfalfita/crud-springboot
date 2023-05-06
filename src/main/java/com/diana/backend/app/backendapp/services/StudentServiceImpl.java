package com.diana.backend.app.backendapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diana.backend.app.backendapp.models.entities.Student;
import com.diana.backend.app.backendapp.repositories.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService{

  @Autowired
  private StudentRepository repository;

  @Override
  @Transactional(readOnly = true)
  public List<Student> findAll() {
    return (List<Student>) repository.findAll();
  }

  @Override
  @Transactional
  public Student save(Student user) {
    return repository.save(user);
  }
}
