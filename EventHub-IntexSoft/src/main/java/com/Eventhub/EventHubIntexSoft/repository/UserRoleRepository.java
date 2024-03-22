package com.Eventhub.EventHubIntexSoft.repository;

import com.Eventhub.EventHubIntexSoft.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
  UserRole findUserRoleByRole(String role);
}
