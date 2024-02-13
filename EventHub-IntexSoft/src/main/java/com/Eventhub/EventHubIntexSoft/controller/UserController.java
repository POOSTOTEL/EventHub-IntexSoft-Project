package com.Eventhub.EventHubIntexSoft.controller;

import com.Eventhub.EventHubIntexSoft.dto.UserDto;
import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import com.Eventhub.EventHubIntexSoft.exception.FormatException;
import com.Eventhub.EventHubIntexSoft.exception.NonUniqValueException;
import com.Eventhub.EventHubIntexSoft.exception.NotFoundException;
import com.Eventhub.EventHubIntexSoft.service.UserService;
import com.Eventhub.EventHubIntexSoft.validator.UserValidator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
  private final UserService userService;
  private final UserValidator userValidator;

  @GetMapping("/all")
  public ResponseEntity<List<UserDto>> allUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @PostMapping
  public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto)
      throws FormatException, EmptyDtoFieldException, NonUniqValueException {
    userValidator.validateUserDtoSave(userDto);
    return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.OK);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserDto> getUserByUserId(@PathVariable("userId") Long userId)
      throws NotFoundException {
    userValidator.validateUserExistingByUserId(userId);
    return new ResponseEntity<>(userService.getUserByUserId(userId), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto)
      throws FormatException, EmptyDtoFieldException, NonUniqValueException, NotFoundException {
    userValidator.validateUserDtoUpdate(userDto);
    return new ResponseEntity<>(userService.updateUser(userDto), HttpStatus.OK);
  }

  @PatchMapping
  public ResponseEntity<UserDto> patchUser(@RequestBody UserDto userDto)
      throws FormatException, NonUniqValueException, NotFoundException, EmptyDtoFieldException {
    userValidator.validateUserDtoPatch(userDto);
    userService.patchUser(userDto);
    return new ResponseEntity<>(userService.getUserByUserId(userDto.getUserId()), HttpStatus.OK);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<String> deleteUserByUserId(@PathVariable("userId") Long userId)
      throws NotFoundException {
    userValidator.validateUserExistingByUserId(userId);
    userService.deleteUserByUserId(userId);
    return new ResponseEntity<>("User with id " + userId + " deleted.", HttpStatus.OK);
  }
}
