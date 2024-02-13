package com.Eventhub.EventHubIntexSoft.dto;

import com.Eventhub.EventHubIntexSoft.enumiration.ParticipantStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Details about the ParticipantDto")
public class ParticipantDto {

  private Long participantId;

  private Long userId;

  private Long eventId;

  private String status;
}
