package com.Eventhub.EventHubIntexSoft.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Schema(description = "Details about the CommentDto")
public class CommentDto {

  private Long commentId;

  private Long eventId;

  private Long userId;

  private String comment;

  private Integer rating;

  private LocalDateTime creationTime;

  private LocalDateTime updateTime;
}
