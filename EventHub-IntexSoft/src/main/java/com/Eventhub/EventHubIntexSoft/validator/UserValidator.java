package com.Eventhub.EventHubIntexSoft.validator;

import com.Eventhub.EventHubIntexSoft.dto.UserDto;
import com.Eventhub.EventHubIntexSoft.entity.User;
import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import com.Eventhub.EventHubIntexSoft.exception.FormatException;
import com.Eventhub.EventHubIntexSoft.exception.NonUniqValueException;
import com.Eventhub.EventHubIntexSoft.exception.NotFoundException;
import com.Eventhub.EventHubIntexSoft.repository.UserRepository;
import java.util.Objects;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserValidator extends FieldValidator {
  @Autowired protected UserRepository userRepository;
  private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";

  private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
  private static final String PASSWORD_PATTERN =
      "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

  private static final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);

  public void validateUserName(String userName, Long userId)
      throws EmptyDtoFieldException, NonUniqValueException {
    validateNullField(userName);
    validateUniqUserName(userName, userId);
  }

  public void validateEmail(String email, Long userId)
      throws EmptyDtoFieldException, NonUniqValueException, FormatException {
    validateNullField(email);
    validateCorrectEmailFormat(email);
    validateUniqEmail(email, userId);
  }

  public void validatePassword(String password) throws EmptyDtoFieldException, FormatException {
    validateNullField(password);
    validateCorrectPasswordFormat(password);
  }

  public void validateUserDtoSave(UserDto userDto)
      throws EmptyDtoFieldException, NonUniqValueException, FormatException {
    validateUserName(userDto.getUserName(), userDto.getUserId());
    validateEmail(userDto.getEmail(), userDto.getUserId());
    validatePassword(userDto.getPassword());
  }

  public void validateUserDtoUpdate(UserDto userDto)
      throws EmptyDtoFieldException, NonUniqValueException, NotFoundException, FormatException {
    validateUserExistingByUserId(userDto.getUserId());
    validateUserDtoSave(userDto);
  }

  public void validateUserDtoPatch(UserDto userDto)
      throws NonUniqValueException, NotFoundException, EmptyDtoFieldException, FormatException {
    validateUserExistingByUserId(userDto.getUserId());
    if (Objects.nonNull(userDto.getUserName())) {
      validateUserName(userDto.getUserName(), userDto.getUserId());
    }
    if (Objects.nonNull(userDto.getEmail())) {
      validateEmail(userDto.getEmail(), userDto.getUserId());
    }
    if (Objects.nonNull(userDto.getPassword())) {
      validatePassword(userDto.getPassword());
    }
  }

  public void validateUserExistingByUserId(Long userId) throws NotFoundException {
    if (Objects.isNull(userRepository.findUserByUserId(userId))) {
      throw new NotFoundException();
    }
  }

  public void validateUniqUserName(String userName, Long userId) throws NonUniqValueException {
    User user = userRepository.findUserByUserName(userName);
    if (!Objects.isNull(user) && !user.getUserId().equals(userId)) {
      throw new NonUniqValueException();
    }
  }

  public void validateCorrectEmailFormat(String email) throws FormatException {
    if (!emailPattern.matcher(email).find()) {
      throw new FormatException();
    }
  }

  public void validateUniqEmail(String email, Long userId) throws NonUniqValueException {
    User user = userRepository.findUserByEmail(email);
    if (Objects.nonNull(user) && !user.getUserId().equals(userId)) {
      throw new NonUniqValueException();
    }
  }

  public void validateCorrectPasswordFormat(String password) throws FormatException {
    if (!passwordPattern.matcher(password).find()) {
      throw new FormatException();
    }
  }
}
