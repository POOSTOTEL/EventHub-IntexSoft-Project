package com.Eventhub.EventHubIntexSoft.DTO;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventDto {
  private Long id;
  private String title;
  private String description;
  private LocalDateTime eventDate;
  private String location;
}
