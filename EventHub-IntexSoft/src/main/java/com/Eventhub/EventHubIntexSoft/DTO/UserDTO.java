package com.Eventhub.EventHubIntexSoft.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String userName;
    private String email;
    private String password;
}