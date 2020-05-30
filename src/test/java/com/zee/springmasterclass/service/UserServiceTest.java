package com.zee.springmasterclass.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.zee.springmasterclass.doa.FakeDataDao;
import com.zee.springmasterclass.model.Gender;
import com.zee.springmasterclass.model.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserServiceTest {

  @Mock
  private FakeDataDao fakeDataDao;
  private UserService userService;


  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    userService = new UserService(fakeDataDao);
  }

  @Test
  void shouldGetAllUsers() {
    UUID uuid = UUID.randomUUID();
    User anna = new User(uuid, "Anna", "Testerson", Gender.MALE, 25, "test@gmail.com");

    List<User> users = new ArrayList<>(Collections.singletonList(anna));

    given(fakeDataDao.selectAllUsers()).willReturn(users);
    List<User> allUsers = userService.getAllUsers(Optional.empty());
    assertThat(allUsers).hasSize(1);
  }

  @Test
  void shouldGetAllUsersByGender() {
    UUID uuidAnna = UUID.randomUUID();
    User anna = new User(uuidAnna, "Anna", "Testerson", Gender.FEMALE, 25, "test@gmail.com");

    UUID uuidFrank = UUID.randomUUID();
    User frank = new User(uuidFrank, "frank", "frankerson", Gender.MALE, 30, "male@gmail.com");

    List<User> users = new ArrayList<>(Arrays.asList(anna, frank));

    given(fakeDataDao.selectAllUsers()).willReturn(users);

    List<User> filteredUsers = userService.getAllUsers(Optional.of("MALE"));
    assertThat(filteredUsers).hasSize(1);
    assertThat(filteredUsers.get(0).getGender()).isEqualTo(Gender.MALE);
  }

  @Test
  void shouldThrowExceptionWhenGenderIsInvalid() throws Exception {
    assertThatThrownBy(() -> userService
        .getAllUsers(Optional.of("asdasd")))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("Invalid gender ");
  }

  @Test
  void shouldGetUserById() {
    UUID uuidAnna = UUID.randomUUID();
    User anna = new User(uuidAnna, "Anna", "Testerson", Gender.MALE, 25, "test@gmail.com");

    UUID uuidFrank = UUID.randomUUID();
    User frank = new User(uuidFrank, "frank", "Testerson", Gender.FEMALE, 25, "frank@gmail.com");

    given(fakeDataDao.selectUser(uuidFrank)).willReturn(Optional.of(frank));
    Optional<User> user = userService.getUser(uuidFrank);

    assertThat(user.isPresent()).isTrue();
    User userFrank = user.get();

    assertThat(userFrank.getFirstName()).isEqualTo(user.get().getFirstName());
  }

  @Test
  void shouldUpdateUser() {
    UUID uuidAnna = UUID.randomUUID();
    User anna = new User(uuidAnna, "Anna", "Testerson", Gender.MALE, 25, "test@gmail.com");

    given(fakeDataDao.selectUser(uuidAnna)).willReturn(Optional.of(anna));
    given(fakeDataDao.updateUser(anna)).willReturn(1);

    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

    int updateResult = userService.updateUser(anna);
    int unknownUserResult = userService
        .updateUser(new User(UUID.randomUUID(), "bob", "bobs", Gender.MALE, 20, "@gmail"));

    verify(fakeDataDao).selectUser(uuidAnna);
    verify(fakeDataDao).updateUser(captor.capture());

    User captorValue = captor.getValue();
    assertThat(captorValue).isEqualToComparingFieldByField(anna);
    assertThat(updateResult).isEqualTo(1);
    assertThat(unknownUserResult).isEqualTo(-1);
  }

  @Test
  void shouldRemoveUser() {
    UUID uuidAnna = UUID.randomUUID();
    User anna = new User(uuidAnna, "Anna", "Testerson", Gender.MALE, 25, "test@gmail.com");
    fakeDataDao.insertUser(uuidAnna, anna);

    given(fakeDataDao.selectUser(uuidAnna)).willReturn(Optional.of(anna));
    given(fakeDataDao.deleteUser(anna.getUserId())).willReturn(1);

    ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);

    int deleteResult = userService.removeUser(uuidAnna);
    int deleteUnknownUserResult = userService.removeUser(UUID.randomUUID());

    verify(fakeDataDao).selectUser(uuidAnna);
    verify(fakeDataDao).deleteUser(captor.capture());

    UUID captorValue = captor.getValue();
    assertThat(captorValue).isEqualTo(uuidAnna);

    assertThat(deleteResult).isEqualTo(1);
    assertThat(deleteUnknownUserResult).isEqualTo(-1);
  }

  @Test
  void shouldAddUser() {
    UUID uuidAnna = UUID.randomUUID();
    User anna = new User(uuidAnna, "Anna", "Testerson", Gender.MALE, 25, "test@gmail.com");

    given(fakeDataDao.insertUser(any(UUID.class), eq(anna))).willReturn(1);

    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

    int insertResult = userService.addUser(anna);

    verify(fakeDataDao).insertUser(any(UUID.class), captor.capture());

    User userCaptor = captor.getValue();

    assertThat(anna).isEqualToComparingFieldByField(userCaptor);
    assertThat(insertResult).isEqualTo(1);

  }
}