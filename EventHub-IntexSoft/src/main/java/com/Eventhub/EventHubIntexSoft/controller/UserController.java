package com.Eventhub.EventHubIntexSoft.controller;

import com.Eventhub.EventHubIntexSoft.dto.UserDto;
import com.Eventhub.EventHubIntexSoft.exception.EmailFormatException;
import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import com.Eventhub.EventHubIntexSoft.exception.NonUniqValueException;
import com.Eventhub.EventHubIntexSoft.exception.NotFoundException;
import com.Eventhub.EventHubIntexSoft.exception.PasswordFormatException;
import com.Eventhub.EventHubIntexSoft.service.UserService;
import com.Eventhub.EventHubIntexSoft.validator.user.UserValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "UserController", description = "Provides CRUD functionality for the User entity")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
  private final UserService userService;
  private final UserValidator userValidator;

  @GetMapping("/all")
  @Operation(
      method = "GET",
      summary = "Get all users",
      description = "Fetch all users and return them in a list.")
  @Parameters()
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserDto.class))
            })
      })
  public ResponseEntity<List<UserDto>> allUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @PostMapping
  @Operation(
      summary = "Create a new user",
      description =
          "Create a new user and return the created user if successful, or a conflict status if not successful.")
  @Parameters({
    @Parameter(
        name = "user",
        required = true,
        description = "User to be created",
        schema = @Schema(implementation = UserDto.class))
  })
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "User created successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserDto.class))
            }),
        @ApiResponse(
            responseCode = "409",
            description = "User creation failed due to conflict",
            content = @Content)
      })
  public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto)
      throws EmailFormatException,
          PasswordFormatException,
          EmptyDtoFieldException,
          NonUniqValueException {
    userValidator.validateUserDtoSave(userDto);
    return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.OK);
  }

  @GetMapping("/{userId}")
  @Operation(
      summary = "Get a user by ID",
      description =
          "Fetch a user by their ID and return the user if found, or a not found status if not found.")
  @Parameters({
    @Parameter(
        name = "id",
        required = true,
        description = "ID of the user to be fetched",
        schema = @Schema(type = "integer", format = "int64"))
  })
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "User found successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
      })
  public ResponseEntity<UserDto> getUserByUserId(@PathVariable("userId") Long userId)
      throws NotFoundException {
    userValidator.validateUserExistingById(userId);
    return new ResponseEntity<>(userService.getUserByUserId(userId), HttpStatus.OK);
  }

  @PutMapping
  @Operation(
      summary = "Update a user",
      description =
          "Update a user and return the updated user if successful, or a not found status if not successful.")
  @Parameters({
    @Parameter(
        name = "userDto",
        required = true,
        description = "UserDto to be updated",
        schema =
            @Schema(
                example =
                    "{\"userId\":\"3\", "
                        + "\"userName\": null, "
                        + "\"email\":\"john@example.com\", "
                        + "\"password\": null}"))
  })
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "User updated successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema =
                      @Schema(
                          example =
                              "{\"userId\":\"3\", "
                                  + "\"userName\":\"Bobby\", "
                                  + "\"email\":\"john@example.com\", "
                                  + "\"password\":\"popit04\"}"))
            }),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
      })
  public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto)
      throws EmailFormatException,
          PasswordFormatException,
          EmptyDtoFieldException,
          NonUniqValueException,
          NotFoundException {
    userValidator.validateUserDtoUpdate(userDto);
    userService.updateUser(userDto);
    return new ResponseEntity<>(userDto, HttpStatus.OK);
  }

  @PatchMapping
  public ResponseEntity<UserDto> patchUser(@RequestBody UserDto userDto)
      throws EmailFormatException,
          PasswordFormatException,
          NonUniqValueException,
          NotFoundException,
          EmptyDtoFieldException {
    userValidator.validateUserDtoPatch(userDto);
    userService.patchUser(userDto);
    return new ResponseEntity<>(userService.getUserByUserId(userDto.getUserId()), HttpStatus.OK);
  }

  @DeleteMapping("/{userId}")
  @Operation(
      summary = "Delete a user by ID",
      description =
          "Delete a user by their ID and return a success message if successful, or a not found status if not successful.")
  @Parameters({
    @Parameter(
        name = "id",
        required = true,
        description = "ID of the user to be deleted",
        schema = @Schema(type = "integer", format = "int64"))
  })
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "User deleted successfully",
            content = {@Content(mediaType = "text/plain", schema = @Schema(type = "string"))}),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
      })
  public ResponseEntity<String> deleteUserByUserId(@PathVariable("userId") Long userId)
      throws NotFoundException {
    userValidator.validateUserExistingById(userId);
    userService.deleteUserByUserId(userId);
    return new ResponseEntity<>("User with id " + userId + " deleted.", HttpStatus.OK);
  }
}
