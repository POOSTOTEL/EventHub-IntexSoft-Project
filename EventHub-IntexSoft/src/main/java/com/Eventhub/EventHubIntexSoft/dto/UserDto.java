package com.Eventhub.EventHubIntexSoft.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Details about the UserDto")
public class UserDto {
  @Schema(
      accessMode = Schema.AccessMode.READ_ONLY,
      description = "The unique ID of the UserDto",
      example = "4")
  private Long userId;

  @Schema(description = "The unique username of the UserDto", example = "Alexander Mikhniuk")
  private String userName;

  @Schema(description = "The unique email of the UserDto", example = "alexmix@mail.ru")
  private String email;

  @Schema(description = "The password of the UserDto", example = "12345aA%")
  private String password;
}
