package com.Eventhub.EventHubIntexSoft.service.Impl;

import com.Eventhub.EventHubIntexSoft.DTO.UserDto;
import com.Eventhub.EventHubIntexSoft.entity.User;
import com.Eventhub.EventHubIntexSoft.mapper.UserListMapper;
import com.Eventhub.EventHubIntexSoft.mapper.UserMapper;
import com.Eventhub.EventHubIntexSoft.repository.UserRepository;
import com.Eventhub.EventHubIntexSoft.service.UserService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  public List<UserDto> getAllUsers() {
    return UserListMapper.instance.toDtoList(userRepository.findAll());
  }

  public Optional<UserDto> createUser(User user) {
    return Optional.ofNullable(UserMapper.instance.toUserDto(userRepository.save(user)));
  }

  public Optional<UserDto> getUserByUserId(Long userId) {
    return Optional.ofNullable(
        UserMapper.instance.toUserDto(userRepository.findUserByUserId(userId)));
  }

  public Optional<UserDto> updateUser(UserDto userDto) {
    return Optional.ofNullable(userRepository.getUserByUserId(userDto.getUserId()))
        .filter(
            user ->
                userRepository
                        .findAllByEmailOrUserName(userDto.getEmail(), userDto.getUserName())
                        .isEmpty()
                    || user.getEmail().equals(userDto.getEmail())
                    || user.getUserName().equals(userDto.getUserName()))
        .map(
            user -> {
              Optional.ofNullable(userDto.getUserName()).ifPresent(user::setUserName);
              Optional.ofNullable(userDto.getEmail()).ifPresent(user::setEmail);
              Optional.ofNullable(userDto.getPassword()).ifPresent(user::setPassword);
              return UserMapper.instance.toUserDto(userRepository.save(user));
            });
  }

  @Transactional(isolation = Isolation.READ_COMMITTED)
  public boolean deleteUserByUserId(Long userId) {
    return Optional.ofNullable(userRepository.getUserByUserId(userId))
        .map(
            user -> {
              userRepository.deleteUserByUserId(userId);
              return true;
            })
        .orElse(false);
  }
}
