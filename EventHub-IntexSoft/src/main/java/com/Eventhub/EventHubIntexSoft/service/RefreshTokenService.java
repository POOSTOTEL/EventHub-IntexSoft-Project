package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.entity.RefreshToken;
import java.util.Optional;

public interface RefreshTokenService {

  Optional<RefreshToken> findByToken(String token);

  RefreshToken createRefreshToken(Long userId);

  RefreshToken verifyExpiration(RefreshToken token);

  Integer deleteByUserId(Long userId);
}
