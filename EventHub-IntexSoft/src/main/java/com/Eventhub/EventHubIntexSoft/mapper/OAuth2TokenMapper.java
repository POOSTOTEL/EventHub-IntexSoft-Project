package com.Eventhub.EventHubIntexSoft.mapper;

import com.Eventhub.EventHubIntexSoft.dto.OAuth2TokenDto;
import com.Eventhub.EventHubIntexSoft.entity.OAuth2Token;
import com.Eventhub.EventHubIntexSoft.entity.User;
import com.Eventhub.EventHubIntexSoft.service.UserService;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public abstract class OAuth2TokenMapper {
  @Autowired protected UserService userService;

  @Mapping(source = "userId", target = "user", qualifiedByName = "findUserByUserId")
  public abstract OAuth2Token toOAuth2Token(OAuth2TokenDto oAuth2TokenDto);

  @Mapping(source = "user", target = "userId", qualifiedByName = "userToUserId")
  public abstract OAuth2TokenDto toOAuth2TokenDto(OAuth2Token oAuth2Token);

  public abstract List<OAuth2Token> toOAuth2TokenList(List<OAuth2TokenDto> oAuth2TokenDtoList);

  public abstract List<OAuth2TokenDto> toOAuth2TokenDtoList(List<OAuth2Token> oAuth2TokenList);

  @Named("userToUserId")
  protected Long userToUserId(User user) {
    return user.getUserId();
  }

  @Named("findUserByUserId")
  protected User findUserByUserId(Long userId) {
    return userService.findUserByUserId(userId);
  }
}
