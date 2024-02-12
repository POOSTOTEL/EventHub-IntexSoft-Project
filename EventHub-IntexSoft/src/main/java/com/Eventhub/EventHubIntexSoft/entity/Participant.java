package com.Eventhub.EventHubIntexSoft.entity;

import com.Eventhub.EventHubIntexSoft.enumiration.ParticipantStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
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
  private Long participantId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "event_id", nullable = false)
  private Event event;

  @Enumerated(EnumType.ORDINAL)
  @Column(name = "participant_status")
  private ParticipantStatus status;
}
