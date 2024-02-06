package com.Eventhub.EventHubIntexSoft.validator;

import com.Eventhub.EventHubIntexSoft.dto.UserDto;
import com.Eventhub.EventHubIntexSoft.entity.User;
import com.Eventhub.EventHubIntexSoft.exception.EmailFormatException;
import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import com.Eventhub.EventHubIntexSoft.exception.NonUniqValueException;
import com.Eventhub.EventHubIntexSoft.exception.NotFoundException;
import com.Eventhub.EventHubIntexSoft.exception.PasswordFormatException;
import com.Eventhub.EventHubIntexSoft.repository.UserRepository;
import java.util.Objects;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserValidator {
  @Autowired protected UserRepository userRepository;

  private class UserNameValidator extends FieldValidator {
    @Override
    public void validate(String userName, Long userId)
        throws EmptyDtoFieldException, NonUniqValueException {
      validateNullField(userName);
      validateUniqUserName(userName, userId);
    }

    public void validateUniqUserName(String userName, Long userId) throws NonUniqValueException {
      User user = userRepository.findUserByUserName(userName);
      if (!Objects.isNull(user) && !user.getUserId().equals(userId)) {
        throw new NonUniqValueException();
      }
    }
  }

  private class EmailValidator extends FieldValidator {
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";

    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

    @Override
    public void validate(String email, Long userId)
        throws EmptyDtoFieldException, NonUniqValueException, EmailFormatException {
      validateNullField(email);
      validateCorrectEmailFormat(email);
      validateUniqEmail(email, userId);
    }

    public void validateCorrectEmailFormat(String email) throws EmailFormatException {
      if (!emailPattern.matcher(email).find()) {
        throw new EmailFormatException();
      }
    }

    public void validateUniqEmail(String email, Long userId) throws NonUniqValueException {
      User user = userRepository.findUserByEmail(email);
      if (Objects.nonNull(user) && !user.getUserId().equals(userId)) {
        throw new NonUniqValueException();
      }
    }
  }

  private class PasswordValidator extends FieldValidator {
    private static final String PASSWORD_PATTERN =
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    private static final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);

    @Override
    public void validate(String password) throws EmptyDtoFieldException, PasswordFormatException {
      validateNullField(password);
      validateCorrectPasswordFormat(password);
    }

    public void validateCorrectPasswordFormat(String password) throws PasswordFormatException {
      if (!passwordPattern.matcher(password).find()) {
        throw new PasswordFormatException();
      }
    }
  }

  public void validateUserDtoSave(UserDto userDto)
      throws EmptyDtoFieldException,
          NonUniqValueException,
          EmailFormatException,
          PasswordFormatException {
    new UserNameValidator().validate(userDto.getUserName(), userDto.getUserId());
    new EmailValidator().validate(userDto.getEmail(), userDto.getUserId());
    new PasswordValidator().validate(userDto.getPassword());
  }

  public void validateUserDtoUpdate(UserDto userDto)
      throws EmptyDtoFieldException,
          NonUniqValueException,
          EmailFormatException,
          PasswordFormatException,
          NotFoundException {
    validateUserExistingByUserId(userDto.getUserId());
    validateUserDtoSave(userDto);
  }

  public void validateUserDtoPatch(UserDto userDto)
      throws NonUniqValueException,
          EmailFormatException,
          PasswordFormatException,
          NotFoundException,
          EmptyDtoFieldException {
    validateUserExistingByUserId(userDto.getUserId());
    if (Objects.nonNull(userDto.getUserName())) {
      new UserNameValidator().validate(userDto.getUserName(), userDto.getUserId());
    }
    if (Objects.nonNull(userDto.getEmail())) {
      new EmailValidator().validate(userDto.getEmail(), userDto.getUserId());
    }
    if (Objects.nonNull(userDto.getPassword())) {
      new PasswordValidator().validate(userDto.getPassword());
    }
  }

  public void validateUserExistingByUserId(Long userId) throws NotFoundException {
    if (Objects.isNull(userRepository.findUserByUserId(userId))) {
      throw new NotFoundException();
    }
  }
}
