package com.Eventhub.EventHubIntexSoft.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CommentDto {

  private Long commentId;

  private Long eventId;

  private Long userId;

  private String comment;

  private Integer rating;

  private LocalDateTime creationTime;

  private LocalDateTime updateTime;
}
