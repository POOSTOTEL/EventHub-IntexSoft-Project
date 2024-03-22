package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.dto.OAuth2TokenDto;
import com.Eventhub.EventHubIntexSoft.entity.OAuth2Token;

public interface OAuth2TokenService {
    public OAuth2TokenDto createOAuth2Token (OAuth2Token oAuth2Token);
}
