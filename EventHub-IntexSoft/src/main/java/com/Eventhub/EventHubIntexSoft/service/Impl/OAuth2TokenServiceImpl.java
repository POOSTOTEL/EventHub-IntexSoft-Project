package com.Eventhub.EventHubIntexSoft.service.Impl;

import com.Eventhub.EventHubIntexSoft.dto.OAuth2TokenDto;
import com.Eventhub.EventHubIntexSoft.entity.OAuth2Token;
import com.Eventhub.EventHubIntexSoft.mapper.OAuth2TokenMapper;
import com.Eventhub.EventHubIntexSoft.repository.OAuth2TokenRepository;
import com.Eventhub.EventHubIntexSoft.service.OAuth2TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2TokenServiceImpl implements OAuth2TokenService {
  protected final OAuth2TokenMapper oAuth2TokenMapper;
  protected final OAuth2TokenRepository oAuth2TokenRepository;

  @Override
  public OAuth2TokenDto createOAuth2Token(OAuth2Token oAuth2Token) {
    return oAuth2TokenMapper.toOAuth2TokenDto(oAuth2TokenRepository.save(oAuth2Token));
  }
}
