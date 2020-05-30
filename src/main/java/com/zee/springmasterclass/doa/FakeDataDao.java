package com.zee.springmasterclass.doa;

import com.zee.springmasterclass.model.Gender;
import com.zee.springmasterclass.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class FakeDataDao implements UserDao {

  private Map<UUID, User> database;

  public FakeDataDao() {
    database = new HashMap<>();
    UUID uuid = UUID.randomUUID();
    database.put(uuid, new User(
        uuid,
        "Joe",
        "Smith",
        Gender.MALE,
        20,
        "email@gmail.com"));
  }

  @Override
  public List<User> selectAllUsers() {
    return new ArrayList<>(database.values());
  }

  @Override
  public Optional<User> selectUser(UUID userId) {
    return Optional.ofNullable(database.get(userId));
  }

  @Override
  public int updateUser(User user) {
    database.put(user.getUserId(), user);
    return 1;
  }

  @Override
  public int deleteUser(UUID userId) {
    database.remove(userId);
    return 1;
  }

  @Override
  public int insertUser(UUID userId, User user) {
    database.put(userId, user);
    return 1;
  }
}
