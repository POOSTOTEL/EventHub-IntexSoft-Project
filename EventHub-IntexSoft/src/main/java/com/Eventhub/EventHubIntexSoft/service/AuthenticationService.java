package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.dto.AuthRequestDto;
import com.Eventhub.EventHubIntexSoft.dto.JwtResponseDto;

public interface AuthenticationService {
  JwtResponseDto registry(AuthRequestDto authRequestDto);

  JwtResponseDto authenticate(AuthRequestDto authRequestDto);
}
