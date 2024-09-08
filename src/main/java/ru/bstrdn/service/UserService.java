package ru.bstrdn.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bstrdn.data.dto.User;
import ru.bstrdn.data.entity.UserEntity;
import ru.bstrdn.data.mapper.UserMapper;
import ru.bstrdn.data.repository.UserRepository;

/**
 * Сервис для работы с пользователями
 */
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Transactional
  public User createUser(User user) {
    UserEntity userEntity = userMapper.toEntity(user);
    UserEntity saved = userRepository.save(userEntity);
    return userMapper.toDto(saved);
  }

  @Transactional
  public void deleteUser(Long userId) {
    userRepository.deleteById(userId);
  }

  @Transactional(readOnly = true)
  public User findUserById(Long userId) {
    UserEntity userEntity = userRepository.findById(userId).orElseThrow();
    return userMapper.toDto(userEntity);
  }

  @Transactional
  public void updateUser(Long userId, User user) {
    UserEntity userEntity = userRepository.findById(userId).orElseThrow();
    userMapper.update(user, userEntity);
  }
}
