package com.Eventhub.EventHubIntexSoft.service.Impl;

import com.Eventhub.EventHubIntexSoft.dto.UserDto;
import com.Eventhub.EventHubIntexSoft.entity.User;
import com.Eventhub.EventHubIntexSoft.mapper.UserMapper;
import com.Eventhub.EventHubIntexSoft.repository.UserRepository;
import com.Eventhub.EventHubIntexSoft.service.UserService;
import java.beans.FeatureDescriptor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  public List<UserDto> getAllUsers() {
    return UserMapper.instance.toDtoList(userRepository.findAll());
  }

  public UserDto createUser(UserDto userDto) {
    if (Optional.ofNullable(userRepository.findUserByUserId(userDto.getUserId())).isPresent()) {
      return null;
    } else {
      return UserMapper.instance.toUserDto(
          userRepository.save(UserMapper.instance.toUser(userDto)));
    }
  }

  public UserDto getUserByUserId(Long userId) {
    return UserMapper.instance.toUserDto(userRepository.findUserByUserId(userId));
  }

  public UserDto updateUser(UserDto userDto) {
    User user = userRepository.findUserByUserId(userDto.getUserId());
    BeanUtils.copyProperties(userDto, user, "userId");
    return UserMapper.instance.toUserDto(userRepository.save(user));
  }

  public UserDto patchUser(UserDto userDto) {
    User user = userRepository.findUserByUserId(userDto.getUserId());
    BeanUtils.copyProperties(userDto, user, getNullProperties(userDto));
    return UserMapper.instance.toUserDto(userRepository.save(user));
  }

  public User findUserByUserId(Long userId) {
    return userRepository.findUserByUserId(userId);
  }

  @Transactional(isolation = Isolation.READ_COMMITTED)
  public boolean deleteUserByUserId(Long userId) {
    return Optional.ofNullable(userRepository.findUserByUserId(userId))
        .map(
            user -> {
              userRepository.deleteUserByUserId(userId);
              return true;
            })
        .orElse(false);
  }

  private String[] getNullProperties(UserDto userDto) {
    final BeanWrapper wrapper = new BeanWrapperImpl(userDto);
    return Stream.of(wrapper.getPropertyDescriptors())
        .map(FeatureDescriptor::getName)
        .filter(name -> wrapper.getPropertyValue(name) == null)
        .toArray(String[]::new);
  }
}
