package com.Eventhub.EventHubIntexSoft.payload.request;

import jakarta.validation.constraints.NotBlank;

public record TokenRefreshRequest(@NotBlank String refreshToken) {}
