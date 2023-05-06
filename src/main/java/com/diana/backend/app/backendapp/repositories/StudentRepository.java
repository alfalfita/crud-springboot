package com.diana.backend.app.backendapp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.diana.backend.app.backendapp.models.entities.Student;

public interface StudentRepository extends CrudRepository<Student, Long>{

}
