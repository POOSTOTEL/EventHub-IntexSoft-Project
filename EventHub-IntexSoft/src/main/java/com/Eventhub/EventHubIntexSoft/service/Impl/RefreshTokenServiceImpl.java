package com.Eventhub.EventHubIntexSoft.service.Impl;

import com.Eventhub.EventHubIntexSoft.entity.RefreshToken;
import com.Eventhub.EventHubIntexSoft.exception.TokenRefreshException;
import com.Eventhub.EventHubIntexSoft.repository.RefreshTokenRepository;
import com.Eventhub.EventHubIntexSoft.repository.UserRepository;
import com.Eventhub.EventHubIntexSoft.service.RefreshTokenService;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
  @Value("${eventhub.app.jwt.refresh.expiration-s}")
  private Long refreshTokenDurationS;

  protected final RefreshTokenRepository refreshTokenRepository;

  protected final UserRepository userRepository;

  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  public RefreshToken createRefreshToken(Long userId) {
    return Optional.of(
            RefreshToken.builder()
                .user(userRepository.findUserByUserId(userId))
                .expiryDate(LocalDateTime.now().plusSeconds(refreshTokenDurationS))
                .token(UUID.randomUUID().toString())
                .build())
        .map(refreshTokenRepository::save)
        .get();
  }

  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
      refreshTokenRepository.delete(token);
      throw new TokenRefreshException(
          token.getToken(), "Refresh token was expired. Please make a new signin request");
    }

    return token;
  }

  public Integer deleteByUserId(Long userId) {
    return refreshTokenRepository.deleteByUserUserId(userId);
  }
}
