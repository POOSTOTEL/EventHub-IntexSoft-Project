package com.Eventhub.EventHubIntexSoft.validator.user;

import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import com.Eventhub.EventHubIntexSoft.exception.PasswordFormatException;
import com.Eventhub.EventHubIntexSoft.repository.UserRepository;
import com.Eventhub.EventHubIntexSoft.validator.FieldValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;
@Service
public class PasswordValidator implements FieldValidator {
  private static final String PASSWORD_PATTERN =
      "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

  private static final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);

  @Override
  public void validate(String password, Long userId)
      throws EmptyDtoFieldException, PasswordFormatException {
    validateNullField(password);
    validateCorrectPasswordFormat(password);
  }

  public void validateCorrectPasswordFormat(String password) throws PasswordFormatException {
    if (!passwordPattern.matcher(password).find()) {
      throw new PasswordFormatException();
    }
  }
}
