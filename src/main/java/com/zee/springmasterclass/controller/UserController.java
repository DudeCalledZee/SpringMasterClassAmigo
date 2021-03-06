package com.zee.springmasterclass.controller;

import com.zee.springmasterclass.model.User;
import com.zee.springmasterclass.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.ws.rs.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<User> fetchUsers(@QueryParam("gender") String gender) {
    return userService.getAllUsers(Optional.ofNullable(gender));
  }

  @GetMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      path = "{userId}"
  )
  public ResponseEntity<?> fetchUser(@PathVariable("userId") UUID uuId) {
    Optional<User> userOptional = userService.getUser(uuId);
    if (userOptional.isPresent()) {
      return ResponseEntity.ok(userOptional.get());
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ErrorMessage("User with " + uuId + " Id not found"));
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Integer> addUser(@RequestBody User user) {
    int addUserResult = userService.addUser(user);
    if (addUserResult == 1) {
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.badRequest().build();
  }

  @PutMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Integer> updateUser(@RequestBody User user) {
    int updateUserResult = userService.updateUser(user);
    if (updateUserResult == 1) {
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.badRequest().build();
  }

  @DeleteMapping(
      produces = MediaType.APPLICATION_JSON_VALUE ,
      path = "{userId}"
  )
  public ResponseEntity<Integer> deleteUser(@PathVariable("userId") UUID uuId) {
    int deleteUserResult = userService.removeUser(uuId);
    if (deleteUserResult == 1) {
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.badRequest().build();
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
