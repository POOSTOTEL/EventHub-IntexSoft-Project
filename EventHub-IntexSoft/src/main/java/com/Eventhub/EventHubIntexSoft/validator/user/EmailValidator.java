package com.Eventhub.EventHubIntexSoft.validator.user;

import com.Eventhub.EventHubIntexSoft.entity.User;
import com.Eventhub.EventHubIntexSoft.exception.EmailFormatException;
import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import com.Eventhub.EventHubIntexSoft.exception.NonUniqValueException;
import com.Eventhub.EventHubIntexSoft.repository.UserRepository;
import com.Eventhub.EventHubIntexSoft.validator.FieldValidator;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Pattern;
@Service
public class EmailValidator implements FieldValidator {
  @Autowired
  UserRepository userRepository;
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
