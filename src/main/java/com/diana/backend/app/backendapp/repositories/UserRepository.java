package com.diana.backend.app.backendapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.diana.backend.app.backendapp.models.entities.User;

public interface UserRepository extends CrudRepository<User, Long>{
  
  @Query("SELECT u FROM User u WHERE u.username=?1")
  Optional<User> findByUsername( String username);
}
