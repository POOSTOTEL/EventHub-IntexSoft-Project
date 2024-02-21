package com.Eventhub.EventHubIntexSoft.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ParticipantDto {

  private Long participantId;

  private Long userId;

  private Long eventId;

  private String status;
}
