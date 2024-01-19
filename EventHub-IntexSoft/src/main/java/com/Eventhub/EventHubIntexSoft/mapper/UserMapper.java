package com.Eventhub.EventHubIntexSoft.mapper;

import com.Eventhub.EventHubIntexSoft.DTO.UserDto;
import com.Eventhub.EventHubIntexSoft.entity.User;
import org.mapstruct.Mapper;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper instance = getMapper(UserMapper.class);

    User toUser(UserDto userDTO);

    UserDto toUserDto(User user);
}
