package com.Eventhub.EventHubIntexSoft.DTO;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDto {
  private Long id;
  private EventDto eventDTO;
  private UserDto userDTO;
  private String comment;
  private Integer rating;
  private LocalDateTime commentDate;
}
