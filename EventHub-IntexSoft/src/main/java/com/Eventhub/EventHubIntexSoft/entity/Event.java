package com.Eventhub.EventHubIntexSoft.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
@Schema(description = "Details about the Event")
public class Event {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "events_seq")
  @SequenceGenerator(name = "events_seq", sequenceName = "events_event_id_seq", allocationSize = 1)
  @Column(name = "event_id")
  @Schema(
      accessMode = Schema.AccessMode.READ_ONLY,
      description = "The unique ID of the Event",
      example = "11")
  private Long eventId;

  @Column(name = "title", nullable = false)
  @Schema(description = "The title of the Event", example = "Spring Festival")
  private String title;

  @Column(name = "description")
  @Schema(
      description = "The description of the Event",
      example = "A festival to celebrate the arrival of spring")
  private String description;

  @Column(name = "event_date")
  @Schema(description = "The date of the Event", example = "2024-03-21T10:15:30")
  private LocalDateTime eventDate;

  @Column(name = "location")
  @Schema(description = "The location of the Event", example = "Minsk, Belarus")
  private String location;

  @OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE)
  @Schema(description = "The comments related to the Event", implementation = Comment.class)
  private List<Comment> eventComments;

  @OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE)
  @Schema(description = "The participants of the Event", implementation = Participant.class)
  private List<Participant> eventParticipants;
}
