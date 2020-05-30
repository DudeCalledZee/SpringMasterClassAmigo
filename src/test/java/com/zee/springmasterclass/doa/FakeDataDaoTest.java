package com.zee.springmasterclass.doa;


import static org.assertj.core.api.Assertions.assertThat;

import com.zee.springmasterclass.model.Gender;
import com.zee.springmasterclass.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FakeDataDaoTest {

  private FakeDataDao fakeDataDao;

  @BeforeEach
  void setUp() {
    fakeDataDao = new FakeDataDao();
  }

  @Test
  void shouldSelectAllUsers() {
    List<User> users = fakeDataDao.selectAllUsers();
    assertThat(users).hasSize(1);
    User user = users.get(0);
    assertThat(user.getAge()).isEqualTo(20);
    assertThat(user.getFirstName()).isEqualTo("Joe");
    assertThat(user.getLastName()).isEqualTo("Smith");
    assertThat(user.getEmail()).isEqualTo("email@gmail.com");
    assertThat(user.getGender()).isEqualTo(Gender.MALE);
    assertThat(user.getUserId()).isNotNull();
  }

  @Test
  void shouldSelectUserByUserId() {
    UUID uuid = UUID.randomUUID();
    User anna = new User(uuid, "Anna", "Testerson", Gender.MALE, 25, "test@gmail.com");
    fakeDataDao.insertUser(uuid, anna);
    assertThat(anna.getUserId()).isEqualTo(uuid);
    assertThat(fakeDataDao.selectAllUsers()).hasSize(2);
    Optional<User> annaOptional = fakeDataDao.selectUser(uuid);
    assertThat(annaOptional.isPresent()).isTrue();
    assertThat(annaOptional.get()).isEqualToComparingFieldByField(anna);
  }

  @Test
  void shouldNotFindAnyUserByRandomUUID() {
    Optional<User> user = fakeDataDao.selectUser(UUID.randomUUID());
    assertThat(user.isPresent()).isFalse();
  }

  @Test
  void shouldUpdateUser() {
    UUID joeId = fakeDataDao.selectAllUsers().get(0).getUserId();
    User newJoe = new User(joeId, "Anna", "Testerson", Gender.MALE, 25, "test@gmail.com");
    fakeDataDao.updateUser(newJoe);
    Optional<User> user = fakeDataDao.selectUser(joeId);
    assertThat(user.isPresent()).isTrue();
    assertThat(user.get()).isEqualToComparingFieldByField(newJoe);
  }

  @Test
  void shouldDeleteUser() {
    UUID joeId = fakeDataDao.selectAllUsers().get(0).getUserId();
    fakeDataDao.deleteUser(joeId);
    assertThat(fakeDataDao.selectAllUsers().size()).isEqualTo(0);
    assertThat(fakeDataDao.selectUser(joeId).isPresent()).isFalse();
  }

  @Test
  void shouldInsertUser() {
    UUID newUserId = UUID.randomUUID();
    User newUser = new User(newUserId, "Sam","Samson",Gender.FEMALE,40, "sam@gmail.com");
    fakeDataDao.insertUser(newUserId,newUser);
    assertThat(fakeDataDao.selectAllUsers().size()).isEqualTo(2);
    System.out.println(fakeDataDao.selectAllUsers());
  }
}