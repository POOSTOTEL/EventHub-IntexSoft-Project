package com.Eventhub.EventHubIntexSoft.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
  private Long id;

  @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
  @JoinColumn(name = "event_id", nullable = false)
  private Event event;

  @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "comment")
  private String comment;

  @Column(name = "rating")
  private Integer rating;

  @Column(name = "comment_date")
  private LocalDateTime commentDate;
}
