package com.zee.springmasterclass.controller;

import com.zee.springmasterclass.model.User;
import com.zee.springmasterclass.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<User> fetchUsers() {
    return userService.getAllUsers();
  }

  @GetMapping(path = "{userId}")
  public ResponseEntity<?> fetchUser(@PathVariable("userId") UUID uuId) {
    Optional<User> userOptional = userService.getUser(uuId);
    if (userOptional.isPresent()) {
      return ResponseEntity.ok(userOptional.get());
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("User with " + uuId + " Id not found"));
  }

  class ErrorMessage {
    String errorMessage;

    public ErrorMessage(String errorMessage) {
      this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
      return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
      this.errorMessage = errorMessage;
    }
  }

}
