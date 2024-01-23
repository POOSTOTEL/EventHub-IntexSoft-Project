package com.Eventhub.EventHubIntexSoft.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "participants")
public class Participant {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participants_seq")
  @SequenceGenerator(
      name = "participants_seq",
      sequenceName = "participants_participant_id_seq",
      allocationSize = 1)
  @Column(name = "participant_id")
  private Long id;

  @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
  @JoinColumn(name = "event_id", nullable = false)
  private Event event;
}
