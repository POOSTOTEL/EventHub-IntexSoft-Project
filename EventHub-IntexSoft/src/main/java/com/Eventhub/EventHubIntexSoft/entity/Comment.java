package com.Eventhub.EventHubIntexSoft.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "comments")
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_seq")
  @SequenceGenerator(
      name = "comments_seq",
      sequenceName = "comments_comment_id_seq",
      allocationSize = 1)
  @Column(name = "comment_id")
  private Long commentId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "event_id", nullable = false)
  private Event event;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "comment")
  private String comment;

  @Column(name = "rating", nullable = false)
  private Integer rating;

  @Column(name = "comment_date")
  private LocalDateTime creationTime;

  @Column(name = "update_date")
  private LocalDateTime updateTime;
}
