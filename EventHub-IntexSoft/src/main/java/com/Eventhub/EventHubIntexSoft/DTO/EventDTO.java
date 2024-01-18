package com.Eventhub.EventHubIntexSoft.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class EventDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime eventDate;
    private String location;
}
