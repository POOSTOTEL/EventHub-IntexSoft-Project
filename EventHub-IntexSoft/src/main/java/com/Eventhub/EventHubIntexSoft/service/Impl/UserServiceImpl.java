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
import org.springframework.transaction.annotation.Propagation;
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

  public Optional<UserDto> getUserById(Long id) {
    return Optional.ofNullable(UserMapper.instance.toUserDto(userRepository.findUserById(id)));
  }

  public Optional<UserDto> updateUser(UserDto userDto) {
    return Optional.ofNullable(userRepository.findUserById(userDto.getId()))
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

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
  public boolean deleteUserById(Long id) {
    return Optional.ofNullable(userRepository.findUserById(id))
        .map(
            user -> {
              userRepository.deleteUserById(id);
              return true;
            })
        .orElse(false);
  }
}
