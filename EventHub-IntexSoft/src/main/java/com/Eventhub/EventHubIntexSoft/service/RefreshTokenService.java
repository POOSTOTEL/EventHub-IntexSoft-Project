package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.entity.RefreshToken;
import com.Eventhub.EventHubIntexSoft.payload.request.TokenRefreshRequest;
import com.Eventhub.EventHubIntexSoft.payload.response.TokenRefreshResponse;

import java.util.Optional;

public interface RefreshTokenService {

  TokenRefreshResponse giveAccessToken(TokenRefreshRequest refreshToken);

  Optional<RefreshToken> findByToken(String token);

  RefreshToken createRefreshToken(Long userId);

  RefreshToken verifyExpiration(RefreshToken token);

  Integer deleteByUserId(Long userId);
}
