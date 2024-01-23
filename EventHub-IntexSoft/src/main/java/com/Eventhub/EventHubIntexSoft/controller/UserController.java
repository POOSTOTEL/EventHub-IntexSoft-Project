package com.Eventhub.EventHubIntexSoft.controller;

import com.Eventhub.EventHubIntexSoft.DTO.UserDto;
import com.Eventhub.EventHubIntexSoft.entity.User;
import com.Eventhub.EventHubIntexSoft.service.Impl.UserServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
  private final UserServiceImpl userServiceImpl;

  @GetMapping("/all")
  public ResponseEntity<List<UserDto>> allUsers() {
    return ResponseEntity.ok(userServiceImpl.getAllUsers());
  }

  @PostMapping
  public ResponseEntity<UserDto> createUser(@RequestBody User user) {
    return userServiceImpl
        .createUser(user)
        .map(userDto -> new ResponseEntity<>(userDto, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
    return userServiceImpl
        .getUserById(id)
        .map(userDto -> new ResponseEntity<>(userDto, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping
  public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
    return userServiceImpl
        .updateUser(userDto)
        .map(userDataTransferObject -> new ResponseEntity<>(userDataTransferObject, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
    return userServiceImpl.deleteUserById(id)
        ? ResponseEntity.ok("User with id " + id + " deleted.")
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
