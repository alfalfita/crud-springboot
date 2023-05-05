package com.diana.backend.app.backendapp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.diana.backend.app.backendapp.models.entities.User;
import com.diana.backend.app.backendapp.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(originPatterns = "*")
public class UserController {
  
  @Autowired
  private UserService userService;

  @GetMapping("/listAll")
  public List<User> list(){
    return userService.findAll();
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<?> show(@PathVariable(name="id") Long idUser){
    Optional<User> userOptional = userService.findById(idUser);

    if(userOptional.isPresent()){
      return ResponseEntity.ok(userOptional.orElseThrow());
    }
    return ResponseEntity.notFound().build();
  }

  /*
  @PostMapping("/add")
  @ResponseStatus(HttpStatus.CREATED)
  public User create(@RequestBody User user){
    return userService.save(user);
  }*/

  @PostMapping("/add")
  public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result){
    if(result.hasErrors()){
      return validation(result);
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id){
    
    /*
    Optional<User> o = userService.findById(id);
    if(o.isPresent()){
      User userDb = o.orElseThrow();
      userDb.setFirstName(user.getFirstName());
      userDb.setLastName(user.getLastName());
      userDb.setEmail(user.getEmail());
      return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDb));
    } */

    if(result.hasErrors()){
      return validation(result);
    }

    Optional<User> o = userService.update(user, id);
    if(o.isPresent()){
      return ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow());
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id){
    Optional<User> o = userService.findById(id);
    if(o.isPresent()){
      userService.remove(id);
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
