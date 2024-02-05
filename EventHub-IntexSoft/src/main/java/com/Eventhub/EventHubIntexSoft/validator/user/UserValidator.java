package com.Eventhub.EventHubIntexSoft.validator.user;

import com.Eventhub.EventHubIntexSoft.dto.UserDto;
import com.Eventhub.EventHubIntexSoft.exception.EmailFormatException;
import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import com.Eventhub.EventHubIntexSoft.exception.NonUniqValueException;
import com.Eventhub.EventHubIntexSoft.exception.NotFoundException;
import com.Eventhub.EventHubIntexSoft.exception.PasswordFormatException;
import com.Eventhub.EventHubIntexSoft.repository.UserRepository;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserValidator {
  @Autowired protected UserRepository userRepository;
  @Autowired protected UserNameValidator userNameValidator;
  @Autowired protected EmailValidator emailValidator;
  @Autowired protected PasswordValidator passwordValidator;

  public void validateUserDtoSave(UserDto userDto)
      throws EmptyDtoFieldException,
          NonUniqValueException,
          EmailFormatException,
          PasswordFormatException {
    userNameValidator.validate(userDto.getUserName(), userDto.getUserId());
    emailValidator.validate(userDto.getEmail(), userDto.getUserId());
    passwordValidator.validate(userDto.getPassword(), userDto.getUserId());
  }

  public void validateUserDtoUpdate(UserDto userDto)
      throws EmptyDtoFieldException,
          NonUniqValueException,
          EmailFormatException,
          PasswordFormatException,
          NotFoundException {
    validateUserExistingById(userDto.getUserId());
    validateUserDtoSave(userDto);
  }

  public void validateUserDtoPatch(UserDto userDto)
      throws NonUniqValueException,
          EmailFormatException,
          PasswordFormatException,
          NotFoundException,
          EmptyDtoFieldException {
    validateUserExistingById(userDto.getUserId());
    if (Objects.nonNull(userDto.getUserName())) {
      userNameValidator.validate(userDto.getUserName(), userDto.getUserId());
    }
    if (Objects.nonNull(userDto.getEmail())) {
      emailValidator.validate(userDto.getEmail(), userDto.getUserId());
    }
    if (Objects.nonNull(userDto.getPassword())) {
      passwordValidator.validate(userDto.getPassword(), userDto.getUserId());
    }
  }

  public void validateUserExistingById(Long userId) throws NotFoundException {
    if (Objects.isNull(userRepository.findUserByUserId(userId))) {
      throw new NotFoundException();
    }
  }
}
