package com.Eventhub.EventHubIntexSoft.controller;

import com.Eventhub.EventHubIntexSoft.DTO.UserDto;
import com.Eventhub.EventHubIntexSoft.entity.User;
import com.Eventhub.EventHubIntexSoft.mapper.UserMapper;
import com.Eventhub.EventHubIntexSoft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> allUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody User user) {
        return new  ResponseEntity<>(userService.createUser(user).get(), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserDto>> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }
    @PostMapping("/update")
    public ResponseEntity<Optional<UserDto>> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User with id " + id + " deleted.");
    }
    //todo

}