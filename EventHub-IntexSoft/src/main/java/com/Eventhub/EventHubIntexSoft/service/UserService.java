package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.DTO.UserDTO;
import com.Eventhub.EventHubIntexSoft.entity.User;
import com.Eventhub.EventHubIntexSoft.mapper.UserMapper;
import com.Eventhub.EventHubIntexSoft.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            userDTOList.add(UserMapper.toDTO(user));
        }
        return userDTOList;
    }
}