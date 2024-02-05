package com.Eventhub.EventHubIntexSoft.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "comments")
@Schema(description = "Details about the Comment")
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_seq")
  @SequenceGenerator(
      name = "comments_seq",
      sequenceName = "comments_comment_id_seq",
      allocationSize = 1)
  @Column(name = "comment_id")
  @Schema(
      accessMode = Schema.AccessMode.READ_ONLY,
      description = "The unique ID of the Comment",
      example = "5")
  private Long commentId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "event_id", nullable = false)
  @Schema(description = "The event associated with the Comment", implementation = Event.class)
  private Event event;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  @Schema(description = "The user who posted the Comment", implementation = User.class)
  private User user;

  @Column(name = "comment")
  @Schema(description = "The text of the Comment", example = "Great event!")
  private String comment;

  @Column(name = "rating", nullable = false)
  @Schema(
      description = "The rating given by the user",
      example = "9",
      minimum = "1",
      maximum = "10")
  private Integer rating;

  @Column(name = "comment_date")
  @Schema(description = "The date when the Comment was posted", example = "2024-03-21T10:15:30")
  private LocalDateTime commentDate;
}
