package com.Eventhub.EventHubIntexSoft.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Schema(description = "Details about the CommentDto")
public class CommentDto {

  @Schema(
      accessMode = Schema.AccessMode.READ_ONLY,
      description = "The unique ID of the CommentDto",
      example = "5")
  private Long commentId;

  @Schema(description = "The Event id associated with the CommentDto", example = "3")
  private Long eventId;

  @Schema(description = "The User id who posted the CommentDto", example = "2")
  private Long userId;

  @Schema(description = "The text of the CommentDto", example = "Great event!")
  private String comment;

  @Schema(
      description = "The rating given by the user",
      example = "9",
      minimum = "0",
      maximum = "10")
  private Integer rating;

  @Schema(description = "The date when the CommentDto was posted", example = "2024-03-21T10:15:30")
  private LocalDateTime creationTime;

  private LocalDateTime updateTime;
}
