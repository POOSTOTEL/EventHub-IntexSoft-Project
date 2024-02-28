package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.entity.RefreshToken;
import java.util.Optional;

public interface RefreshTokenService {
  RefreshToken createRefreshToken(String email);

  Optional<RefreshToken> findByToken(String token);

  RefreshToken verifyExpiration(RefreshToken token);
}
