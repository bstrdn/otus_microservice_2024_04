package ru.bstrdn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.bstrdn.data.dto.User;
import ru.bstrdn.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

  private final UserService userService;

  @Override
  public ResponseEntity<User> createUser(User user) {
    User saved = userService.createUser(user);
    return ResponseEntity.ok(saved);
  }

  @Override
  public ResponseEntity<Void> deleteUser(Long userId) {
    userService.deleteUser(userId);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<User> findUserById(Long userId) {
    User user = userService.findUserById(userId);
    return ResponseEntity.ok().body(user);
  }

  @Override
  public ResponseEntity<Void> updateUser(Long userId, User user) {
    user.setId(userId);
    userService.updateUser(userId, user);
    return ResponseEntity.ok().build();
  }
}
