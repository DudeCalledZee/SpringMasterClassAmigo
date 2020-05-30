package com.zee.springmasterclass.model;

import java.util.UUID;

public class User {
  private UUID userId;
  private final String firstName;
  private final String lastName;
  private final Gender gender;
  private final Integer age;
  private final String email;

  public User(UUID userId, String firstName, String lastName,
      Gender gender, Integer age, String email) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.age = age;
    this.email = email;
  }

  public UUID getUserId() {
    return userId;
  }

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

  public void setUserId(UUID userId) {
    this.userId = userId;
  }
}
