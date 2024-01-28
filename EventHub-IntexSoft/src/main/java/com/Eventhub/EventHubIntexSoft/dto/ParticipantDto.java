package com.Eventhub.EventHubIntexSoft.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Details about the ParticipantDto")
public class ParticipantDto {

  @Schema(
          accessMode = Schema.AccessMode.READ_ONLY,
          description = "The unique ID of the ParticipantDto",
          example = "12")
  private Long participantId;

  @Schema(description = "The User id associated with the ParticipantDto", implementation = UserDto.class)
  private Long userId;

  @Schema(description = "The Event id associated with the ParticipantDto", implementation = EventDto.class)
  private Long eventId;
}

