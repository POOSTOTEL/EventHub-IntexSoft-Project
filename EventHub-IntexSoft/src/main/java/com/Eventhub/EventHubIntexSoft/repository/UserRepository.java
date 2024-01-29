package com.Eventhub.EventHubIntexSoft.repository;

import com.Eventhub.EventHubIntexSoft.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  void deleteUserByUserId(Long userId);

  List<User> findAllByEmailOrUserName(String email, String userName);

  User findUserByUserId(Long userId);
}
