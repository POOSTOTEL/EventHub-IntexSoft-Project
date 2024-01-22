package com.Eventhub.EventHubIntexSoft.repository;

import com.Eventhub.EventHubIntexSoft.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    void deleteAllByUserId(Long id);

    void deleteAllByEventId(Long id);

    Participant findParticipantById(Long id);

    void deleteParticipantById(Long id);
}
