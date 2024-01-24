package com.Eventhub.EventHubIntexSoft.repository;

import com.Eventhub.EventHubIntexSoft.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

  Participant findParticipantByParticipantId(Long participantId);

  void deleteParticipantByParticipantId(Long participantId);
}
