package com.Eventhub.EventHubIntexSoft.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParticipantDto {
  private Long id;
  private UserDto userDTO;
  private EventDto eventDTO;
}
