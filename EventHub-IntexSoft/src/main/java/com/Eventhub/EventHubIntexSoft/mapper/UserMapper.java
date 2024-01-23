package com.Eventhub.EventHubIntexSoft.mapper;

import static org.mapstruct.factory.Mappers.getMapper;

import com.Eventhub.EventHubIntexSoft.DTO.UserDto;
import com.Eventhub.EventHubIntexSoft.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserMapper instance = getMapper(UserMapper.class);

  User toUser(UserDto userDTO);

  UserDto toUserDto(User user);
}
