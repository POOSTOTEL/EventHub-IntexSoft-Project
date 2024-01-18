package com.Eventhub.EventHubIntexSoft.mapper;

import com.Eventhub.EventHubIntexSoft.DTO.UserDTO;
import com.Eventhub.EventHubIntexSoft.entity.User;

public abstract class UserMapper {
    public static User toUser(UserDTO userDTO) {
        return new User(userDTO.getId(), userDTO.getUserName(), userDTO.getEmail(), userDTO.getPassword());
    }
    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getUserName(), user.getEmail(), user.getPassword());
    }
}
