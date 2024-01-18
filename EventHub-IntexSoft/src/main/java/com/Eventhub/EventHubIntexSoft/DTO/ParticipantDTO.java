package com.Eventhub.EventHubIntexSoft.DTO;

import com.Eventhub.EventHubIntexSoft.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParticipantDTO {
    private Long id;
    private UserDTO userDTO;
    private EventDTO eventDTO;
}
