package com.Eventhub.EventHubIntexSoft.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Details about the EventDto")
public class EventDto {

  @Schema(
      accessMode = Schema.AccessMode.READ_ONLY,
      description = "The unique ID of the EventDto",
      example = "11")
  private Long eventId;

  @Schema(description = "The title of the EventDto", example = "Spring Festival")
  private String title;

  @Schema(
      description = "The description of the EventDto",
      example = "A festival to celebrate the arrival of spring")
  private String description;

  @Schema(description = "The date of the EventDto", example = "2024-03-21T10:15:30")
  private LocalDateTime eventDate;

  @Schema(description = "The location of the EventDto", example = "Minsk, Belarus")
  private String location;
}
