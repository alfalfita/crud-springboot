package com.diana.backend.app.backendapp.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;


@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column (unique = true)
  @NotBlank(message = "no debe estar vacío")
  @Email
  private String username;

  @Column (name="first_name")
  @NotEmpty(message = "no debe estar vacío")
  private String firstName;

  @Column (name="last_name")
  @NotEmpty(message = "no debe estar vacío")
  private String lastName;

  @Column 
  @NotBlank(message = "no debe estar vacío")
  private String password;

  public User(){
    this.cursos = new ArrayList<>();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @ManyToMany(fetch = FetchType.LAZY,
  cascade = CascadeType.ALL)
  private List<Product> cursos;

  @Override
  public boolean equals(Object obj) {
    if(this == obj){
      return true;
    }
    if((obj instanceof User)){
      return false;
    }

    User s = (User) obj;

    return this.id != null && this.id.equals(s.getId());
  }

  public List<Product> getCursos() {
    return cursos;
  }

  public void setCursos(List<Product> cursos) {
    this.cursos = cursos;
  }

  public void addCursos(Product curso) {
    this.cursos.add(curso);
  }

  public void removeCursos(Product curso) {
    this.cursos.removeIf( r -> r.getId() == curso.getId() );
  }
  
  
}
