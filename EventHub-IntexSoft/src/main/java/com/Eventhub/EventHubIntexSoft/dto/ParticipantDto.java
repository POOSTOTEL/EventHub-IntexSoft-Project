package com.Eventhub.EventHubIntexSoft.dto;

import com.Eventhub.EventHubIntexSoft.enumiration.ParticipantStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Details about the ParticipantDto")
public class ParticipantDto {

  @Schema(
      accessMode = Schema.AccessMode.READ_ONLY,
      description = "The unique ID of the ParticipantDto",
      example = "12")
  private Long participantId;

  @Schema(description = "The User id associated with the ParticipantDto", example = "3")
  private Long userId;

  @Schema(description = "The Event id associated with the ParticipantDto", example = "2")
  private Long eventId;

  private String status;
}
