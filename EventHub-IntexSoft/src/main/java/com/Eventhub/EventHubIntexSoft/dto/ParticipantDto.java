package com.Eventhub.EventHubIntexSoft.dto;

import lombok.Data;

@Data
public class ParticipantDto {

  private Long participantId;

  private Long userId;

  private Long eventId;

  private String status;
}
