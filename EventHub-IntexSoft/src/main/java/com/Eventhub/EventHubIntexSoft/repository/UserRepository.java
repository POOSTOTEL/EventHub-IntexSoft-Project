package com.Eventhub.EventHubIntexSoft.repository;

import com.Eventhub.EventHubIntexSoft.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  void deleteUserByUserId(Long userId);

  User findUserByUserName(String userName);

  User findUserByUserId(Long userId);

  User findUserByEmail(String email);
}
