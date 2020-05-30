package com.zee.springmasterclass.doa;

import com.zee.springmasterclass.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UserDao {

  List<User> selectAllUsers();

  Optional<User> selectUser(UUID userId);

  int updateUser(User user);

  int deleteUser(UUID userId);

  int insertUser(UUID userId, User user);

}
