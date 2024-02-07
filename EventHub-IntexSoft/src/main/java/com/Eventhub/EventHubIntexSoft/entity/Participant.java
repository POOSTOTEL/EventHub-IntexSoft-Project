package com.Eventhub.EventHubIntexSoft.entity;

import com.Eventhub.EventHubIntexSoft.enumiration.ParticipantStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "participants")
@Schema(description = "Details about the Participant")
public class Participant {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participants_seq")
  @SequenceGenerator(
      name = "participants_seq",
      sequenceName = "participants_participant_id_seq",
      allocationSize = 1)
  @Column(name = "participant_id")
  @Schema(
      accessMode = Schema.AccessMode.READ_ONLY,
      description = "The unique ID of the participant",
      example = "12")
  private Long participantId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  @Schema(description = "The user associated with the participant", example = "4")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "event_id", nullable = false)
  @Schema(description = "The event associated with the participant", example = "4")
  private Event event;
  @Enumerated(EnumType.ORDINAL)
  @Column(name = "participant_status")
  private ParticipantStatus status;
}
