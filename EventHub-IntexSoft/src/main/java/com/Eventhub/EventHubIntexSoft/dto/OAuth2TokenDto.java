package com.Eventhub.EventHubIntexSoft.dto;

import lombok.Data;

@Data
public class OAuth2TokenDto {

  private Long tokenId;

  private String accessToken;

  private String vendorInfo;

  private Long userId;
}
