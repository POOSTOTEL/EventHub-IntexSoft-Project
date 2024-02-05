package com.Eventhub.EventHubIntexSoft.validator;

import com.Eventhub.EventHubIntexSoft.dto.UserDto;
import com.Eventhub.EventHubIntexSoft.exception.EmailFormatException;
import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import com.Eventhub.EventHubIntexSoft.exception.NonUniqValueException;
import com.Eventhub.EventHubIntexSoft.exception.PasswordFormatException;
import com.Eventhub.EventHubIntexSoft.repository.UserRepository;
import java.util.Objects;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserValidator {
  @Autowired UserRepository userRepository;
  private String errorMessage = new String("");
  private static final String PASSWORD_PATTERN =
      "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
  private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";

  private static final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
  private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

  public void validateUserDtoSaveUpdate(UserDto userDto)
      throws EmptyDtoFieldException,
          NonUniqValueException,
          EmailFormatException,
          PasswordFormatException {
    validateUserId(userDto.getUserId());
    validateUserName(userDto.getUserName());
    validateEmail(userDto.getEmail());
    validatePassword(userDto.getPassword());
  }

  public void validateUserId(Long userId) throws EmptyDtoFieldException {
    if (Objects.isNull(userId)) {
      throw new EmptyDtoFieldException();
    }
  }

  public void validateUserName(String userName)
      throws EmptyDtoFieldException, NonUniqValueException {
    if (Objects.isNull(userName)) {
      throw new EmptyDtoFieldException();
    } else if (Objects.isNull(userRepository.findUserByUserName(userName))) {
      throw new NonUniqValueException();
    }
  }

  public void validateEmail(String email)
      throws EmptyDtoFieldException, EmailFormatException, NonUniqValueException {
    if (Objects.isNull(email)) {
      throw new EmptyDtoFieldException();
    } else if (!emailPattern.matcher(email).find()) {
      throw new EmailFormatException();
    } else if (Objects.isNull(userRepository.findUserByUserName(email))) {
      throw new NonUniqValueException();
    }
  }

  public void validatePassword(String password)
      throws PasswordFormatException, EmptyDtoFieldException {
    if (Objects.isNull(password)) {
      throw new EmptyDtoFieldException();
    } else if (!passwordPattern.matcher(password).find()) {
      throw new PasswordFormatException();
    }
  }
}
