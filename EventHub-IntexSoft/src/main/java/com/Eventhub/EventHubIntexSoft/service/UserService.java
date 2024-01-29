package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.dto.UserDto;
import com.Eventhub.EventHubIntexSoft.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
  List<UserDto> getAllUsers();

  Optional<UserDto> createUser(User user);

  Optional<UserDto> getUserByUserId(Long userId);

  boolean deleteUserByUserId(Long userId);

  User findUserByUserId(Long userId);

  Optional<UserDto> updateUser(UserDto userDto);
}
