package com.Eventhub.EventHubIntexSoft.repository;

import com.Eventhub.EventHubIntexSoft.entity.ParticipantStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantStatusRepository extends JpaRepository<ParticipantStatus, Long> {
    ParticipantStatus findParticipantStatusByStatus(String status);
}
