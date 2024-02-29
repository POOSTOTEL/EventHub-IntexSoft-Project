package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.dto.UserDto;
import com.Eventhub.EventHubIntexSoft.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
  Page<UserDto> getAllUsers(Integer offset, Integer limit);

  UserDto createUser(UserDto userDto);

  UserDto getUserByUserId(Long userId);

  void deleteUserByUserId(Long userId);

  User findUserByUserId(Long userId);

  User findUserByEmail(String email);

  UserDto updateUser(UserDto userDto);

  UserDto patchUser(UserDto userDto);
}
