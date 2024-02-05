package com.Eventhub.EventHubIntexSoft.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;

@Data
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

  @Column(name = "title", nullable = false, unique = true)
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
}
