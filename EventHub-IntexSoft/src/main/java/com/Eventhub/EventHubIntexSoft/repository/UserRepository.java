package com.Eventhub.EventHubIntexSoft.repository;

import com.Eventhub.EventHubIntexSoft.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User getUserByUserId(Long userId);

  void deleteUserByUserId(Long id);

  User findUserByEmail(String email);

  User findUserByUserName(String userName);

  List<User> findAllByEmailOrUserName(String email, String userName);

  User findUserByUserId(Long id);
}
