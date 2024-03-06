package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.entity.RefreshToken;
import com.Eventhub.EventHubIntexSoft.exception.TokenRefreshException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenService {

    Optional<RefreshToken> findByToken(String token);

    RefreshToken createRefreshToken(Long userId);

    RefreshToken verifyExpiration(RefreshToken token);

    Integer deleteByUserId(Long userId);
}
