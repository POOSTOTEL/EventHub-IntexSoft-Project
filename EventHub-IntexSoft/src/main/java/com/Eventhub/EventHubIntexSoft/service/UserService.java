package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.DTO.UserDto;
import com.Eventhub.EventHubIntexSoft.entity.User;
import com.Eventhub.EventHubIntexSoft.mapper.UserListMapper;
import com.Eventhub.EventHubIntexSoft.mapper.UserMapper;
import com.Eventhub.EventHubIntexSoft.repository.CommentRepository;
import com.Eventhub.EventHubIntexSoft.repository.ParticipantRepository;
import com.Eventhub.EventHubIntexSoft.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;
    private final CommentRepository commentRepository;
    public List<UserDto> getAllUsers() {
        return UserListMapper.instance.toDtoList(userRepository.findAll());
    }
    public Optional<UserDto> createUser(User user) {
        return Optional.ofNullable(UserMapper.instance.toUserDto(userRepository.save(user)));
    }
    public Optional<UserDto> getUser(Long id) {
        return Optional.ofNullable(UserMapper.instance.toUserDto(userRepository.findUserById(id)));
    }
    //todo добавить проверку на уникальность всех полей, чтобы исключить повторения email иsername
    public Optional<UserDto> updateUser(User user) {
        User userFromDB = userRepository.findUserById(user.getId());
        if (user.getUserName() != null) {
            userFromDB.setUserName(user.getUserName());
        }
        if (user.getEmail() != null) {
            userFromDB.setEmail(user.getEmail());
        }
        if (user.getPassword() != null) {
            userFromDB.setPassword(user.getPassword());
        }
        return Optional.of(UserMapper.instance.toUserDto(userRepository.save(userFromDB)));
    }
    @Transactional
    public void deleteUser(Long id) {
        commentRepository.deleteAllByUserId(id);
        participantRepository.deleteAllByUserId(id);
        userRepository.deleteUserById(id);
    }
}