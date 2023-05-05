package com.diana.backend.app.backendapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diana.backend.app.backendapp.models.entities.User;
import com.diana.backend.app.backendapp.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

  @Autowired
  private UserRepository repository;

  @Autowired
  private PasswordEncoder passwordEncoder;

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
    String passwordBc = passwordEncoder.encode(user.getPassword());
    user.setPassword(passwordBc);
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

//   private Optional<User> getUserByUsername(User u) {
//     Optional<User> ou = repository.getUserByUsername(u.getUsername());

//     User userOptional = null;
//     if(ou.isPresent()){
//       User userDb = ou.orElseThrow();
//       userDb.setFirstName(u.getFirstName());
//       userDb.setLastName(u.getLastName());
//       userDb.setUsername(u.getUsername());
//       userOptional = this.save(userDb);
//     } 
//     return Optional.ofNullable(userOptional);
// }

  
}
