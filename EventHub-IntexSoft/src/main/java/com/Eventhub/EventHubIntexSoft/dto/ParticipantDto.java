package com.Eventhub.EventHubIntexSoft.DTO;

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

  @Schema(description = "The UserDto associated with the ParticipantDto")
  private UserDto userDTO;

  @Schema(description = "The EventDto associated with the ParticipantDto")
  private EventDto eventDTO;
}

