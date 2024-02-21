package com.Eventhub.EventHubIntexSoft.mapper;

import com.Eventhub.EventHubIntexSoft.dto.UserDto;
import com.Eventhub.EventHubIntexSoft.entity.User;
import com.Eventhub.EventHubIntexSoft.entity.UserRole;
import com.Eventhub.EventHubIntexSoft.repository.UserRoleRepository;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public abstract class UserMapper {

  @Autowired protected UserRoleRepository userRoleRepository;

  @Mapping(source = "roles", target = "roles", qualifiedByName = "stringToUserRole")
  public abstract User toUser(UserDto userDTO);

  @Mapping(source = "roles", target = "roles", qualifiedByName = "userRoleToString")
  public abstract UserDto toUserDto(User user);

  public abstract List<User> toUsertList(List<UserDto> dtoList);

  public abstract List<UserDto> toDtoList(List<User> modelList);

  @Named("stringToUserRole")
  protected UserRole stringToUserRole(String role) {
    return userRoleRepository.findUserRoleByRole(role);
  }

  @Named("userRoleToString")
  protected String userRoleToString(UserRole userRole) {
    return userRole.getRole();
  }
}
