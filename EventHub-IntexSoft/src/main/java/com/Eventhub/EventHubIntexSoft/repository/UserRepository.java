package com.Eventhub.EventHubIntexSoft.repository;

import com.Eventhub.EventHubIntexSoft.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  User findUserById(Long id);

  void deleteUserById(Long id);

  User findUserByEmail(String email);

  User findUserByUserName(String userName);

  List<User> findAllByEmailOrUserName(String email, String userName);
}
