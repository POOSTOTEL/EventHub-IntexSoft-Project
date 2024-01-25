package com.Eventhub.EventHubIntexSoft.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Details about the CommentDto")
public class CommentDto {

  @Schema(
      accessMode = Schema.AccessMode.READ_ONLY,
      description = "The unique ID of the CommentDto",
      example = "5")
  private Long commentId;

  @Schema(description = "The EventDto associated with the CommentDto")
  private EventDto eventDTO;

  @Schema(description = "The UserDto who posted the CommentDto")
  private UserDto userDTO;

  @Schema(description = "The text of the CommentDto", example = "Great event!")
  private String comment;

  @Schema(
      description = "The rating given by the user",
      example = "9",
      minimum = "0",
      maximum = "10")
  private Integer rating;

  @Schema(description = "The date when the CommentDto was posted", example = "2024-03-21T10:15:30")
  private LocalDateTime commentDate;
}
