package com.zee.springmasterclass.service;

import com.zee.springmasterclass.doa.UserDao;
import com.zee.springmasterclass.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserDao userDao;

  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  public List<User> getAllUsers() {
    return userDao.selectAllUsers();
  }

  public Optional<User> getUser(UUID userId) {
    return userDao.selectUser(userId);
  }

  public int updateUser(User user) {
    Optional<User> optionalUser = getUser(user.getUserId());
    if (optionalUser.isPresent()) {
      return userDao.updateUser(user);
    }
    return -1;
  }

  public int removeUser(UUID userId) {
    Optional<User> optionalUser = getUser(userId);
    if (optionalUser.isPresent()) {
      return userDao.deleteUser(userId);
    }
    return -1;
  }

  public int addUser(User user) {
    UUID userId = UUID.randomUUID();
    user.setUserId(userId);
    return userDao.insertUser(userId, user);
  }
}
