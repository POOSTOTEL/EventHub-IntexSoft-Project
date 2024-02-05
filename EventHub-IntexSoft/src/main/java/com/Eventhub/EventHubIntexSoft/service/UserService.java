package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.dto.UserDto;
import com.Eventhub.EventHubIntexSoft.entity.User;
import java.util.List;

public interface UserService {
  List<UserDto> getAllUsers();

  UserDto createUser(UserDto userDto);

  UserDto getUserByUserId(Long userId);

  boolean deleteUserByUserId(Long userId);

  User findUserByUserId(Long userId);

  UserDto updateUser(UserDto userDto);

  UserDto patchUser(UserDto userDto);
}
