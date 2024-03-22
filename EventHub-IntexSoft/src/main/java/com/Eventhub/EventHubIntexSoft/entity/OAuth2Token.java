package com.Eventhub.EventHubIntexSoft.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "oauth2_tokens")
public class OAuth2Token {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oauth2_tokens_seq")
  @SequenceGenerator(
      name = "oauth2_tokens_seq",
      sequenceName = "oauth2_tokens_token_id_seq",
      allocationSize = 1)
  @Column(name = "token_id")
  Long tokenId;

  @Column(name = "access_token")
  String accessToken;

  @Column(name = "vendor_info")
  String vendorInfo;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "user_id", nullable = false)
  User user;
}
