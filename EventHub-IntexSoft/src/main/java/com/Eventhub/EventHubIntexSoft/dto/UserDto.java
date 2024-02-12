package com.Eventhub.EventHubIntexSoft.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Details about the UserDto")
public class UserDto {

  private Long userId;

  private String userName;

  private String email;

  private String password;
}
