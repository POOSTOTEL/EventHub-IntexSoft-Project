package com.Eventhub.EventHubIntexSoft.repository;

import com.Eventhub.EventHubIntexSoft.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByToken(String token);

  Integer deleteByUserUserId(Long userId);
}
