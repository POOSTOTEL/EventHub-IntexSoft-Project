package com.Eventhub.EventHubIntexSoft.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
public class Event {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "events_seq")
  @SequenceGenerator(name = "events_seq", sequenceName = "events_event_id_seq", allocationSize = 1)
  @Column(name = "event_id")
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "event_date")
  private LocalDateTime eventDate;

  @Column(name = "location")
  private String location;
}
