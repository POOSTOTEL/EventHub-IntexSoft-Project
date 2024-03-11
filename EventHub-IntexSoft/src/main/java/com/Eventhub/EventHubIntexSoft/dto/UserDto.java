package com.Eventhub.EventHubIntexSoft.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

  private Long userId;

  private String userName;

  private String email;

  private String password;

  private List<String> roles;
}
