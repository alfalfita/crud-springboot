package com.diana.backend.app.backendapp.models.entities;

import jakarta.persistence.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;


@Entity
@Table(name = "students")
public class Student {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column (unique = true)
  @NotBlank(message = "no debe estar vacío")
  @Email
  private String email;

  @Column
  @NotEmpty(message = "no debe estar vacío")
  private String name;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object obj) {
    if(this == obj){
      return true;
    }
    if((obj instanceof Student)){
      return false;
    }

    Student s = (Student) obj;

    return this.id != null && this.id.equals(s.getId());
  }

  

}
