package com.Eventhub.EventHubIntexSoft.validator.user;

import com.Eventhub.EventHubIntexSoft.entity.User;
import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import com.Eventhub.EventHubIntexSoft.exception.NonUniqValueException;
import com.Eventhub.EventHubIntexSoft.repository.UserRepository;
import com.Eventhub.EventHubIntexSoft.validator.FieldValidator;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
@Service
public class UserNameValidator implements FieldValidator {
  @Autowired
  UserRepository userRepository;
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
