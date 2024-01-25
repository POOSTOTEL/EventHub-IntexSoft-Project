package com.Eventhub.EventHubIntexSoft.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParticipantDto {
  private Long participantId;
  private UserDto userDTO;
  private EventDto eventDTO;
}
