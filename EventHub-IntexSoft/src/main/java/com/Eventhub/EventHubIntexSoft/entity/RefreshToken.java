package com.Eventhub.EventHubIntexSoft.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_tokens_seq")
  @SequenceGenerator(
      name = "refresh_tokens_seq",
      sequenceName = "refresh_tokens_refresh_token_id_seq",
      allocationSize = 1)
  @Column(name = "refresh_token_id")
  public Long refreshTokenId;

  @Column(name = "expiry_date")
  private LocalDateTime expiryDate;

  @Column(name = "token")
  private String token;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private User user;
}
