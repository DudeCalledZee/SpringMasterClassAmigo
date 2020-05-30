package com.zee.springmasterclass.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.UUID;

public class User {
  private final UUID userId;
  private final String firstName;
  private final String lastName;
  private final Gender gender;
  private final Integer age;
  private final String email;

  public User(
      @JsonProperty("userId") UUID userId,
      @JsonProperty("firstName") String firstName,
      @JsonProperty("lastName") String lastName,
      @JsonProperty("gender") Gender gender,
      @JsonProperty("age") Integer age,
      @JsonProperty("email") String email) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.age = age;
    this.email = email;
  }

  public static User newUser(UUID userId, User user){
    return new User(userId, user.getFirstName(), user.getLastName(), user.getGender(), user.getAge(), user.getEmail());
  }

  public UUID getUserId() {
    return userId;
  }

  // @JsonIgnore
  public Gender getGender() {
    return gender;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public Integer getAge() {
    return age;
  }

  public String getEmail() {
    return email;
  }

  public String getFullName() {
    return firstName + " " + lastName;
  }

  public LocalDate getDateOfBirth() {
    return LocalDate.now().minusYears(age);
  }

  @Override
  public String toString() {
    return "User{" +
        "userId=" + userId +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", gender=" + gender +
        ", age=" + age +
        ", email='" + email + '\'' +
        '}';
  }
}
