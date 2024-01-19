package com.Eventhub.EventHubIntexSoft.mapper;

import com.Eventhub.EventHubIntexSoft.DTO.ParticipantDto;
import com.Eventhub.EventHubIntexSoft.DTO.UserDto;
import com.Eventhub.EventHubIntexSoft.entity.Participant;
import com.Eventhub.EventHubIntexSoft.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = "spring", uses = EventMapper.class)
public interface UserListMapper {
    UserListMapper instance = getMapper(UserListMapper.class);

    List<User> toUsertList(List<UserDto> dtoList);

    List<UserDto> toDtoList(List<User> modelList);
}