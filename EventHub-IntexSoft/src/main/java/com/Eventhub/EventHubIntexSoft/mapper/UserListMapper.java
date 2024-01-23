package com.Eventhub.EventHubIntexSoft.mapper;

import static org.mapstruct.factory.Mappers.getMapper;

import com.Eventhub.EventHubIntexSoft.DTO.UserDto;
import com.Eventhub.EventHubIntexSoft.entity.User;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = EventMapper.class)
public interface UserListMapper {
  UserListMapper instance = getMapper(UserListMapper.class);

  List<User> toUsertList(List<UserDto> dtoList);

  List<UserDto> toDtoList(List<User> modelList);
}
