package com.Eventhub.EventHubIntexSoft.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class EventDto {

  private Long eventId;

  private String title;

  private String description;

  private LocalDateTime eventDate;

  private String location;
}
