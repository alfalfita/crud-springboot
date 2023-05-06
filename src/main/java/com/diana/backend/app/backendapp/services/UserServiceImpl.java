package com.diana.backend.app.backendapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diana.backend.app.backendapp.models.entities.User;
import com.diana.backend.app.backendapp.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

  @Autowired
  private UserRepository repository;

  @Override
  @Transactional(readOnly = true)
  public List<User> findAll() {
    return (List<User>) repository.findAll();
  }

  @Override
  public Optional<User> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  @Transactional
  public User save(User user) {

    String plainTextPassword = user.getPassword();
    String hashedPassword = BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());

    user.setPassword(hashedPassword);
    return repository.save(user);
  }

  @Override
  @Transactional
  public void remove(Long id) {
    repository.deleteById(id);
  }

  @Override
  @Transactional
  public Optional<User> update(User user, Long id) {
    Optional<User> o = this.findById(id);
    User userOptional = null;
    if(o.isPresent()){
      User userDb = o.orElseThrow();
      userDb.setFirstName(user.getFirstName());
      userDb.setLastName(user.getLastName());
      userDb.setUsername(user.getUsername());
      userOptional = this.save(userDb);
    } 
    return Optional.ofNullable(userOptional);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<User> findByUsername(String username) {
    return repository.findByUsername(username);
  }
  
}
