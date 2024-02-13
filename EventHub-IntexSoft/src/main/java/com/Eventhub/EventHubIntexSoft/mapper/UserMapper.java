package com.Eventhub.EventHubIntexSoft.mapper;

import static org.mapstruct.factory.Mappers.getMapper;

import com.Eventhub.EventHubIntexSoft.dto.UserDto;
import com.Eventhub.EventHubIntexSoft.entity.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {
  UserMapper instance = getMapper(UserMapper.class);

  User toUser(UserDto userDTO);

  UserDto toUserDto(User user);

  List<User> toUsertList(List<UserDto> dtoList);

  List<UserDto> toDtoList(List<User> modelList);
}
