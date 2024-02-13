package com.Eventhub.EventHubIntexSoft.repository;

import com.Eventhub.EventHubIntexSoft.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

  Participant findParticipantByParticipantId(Long participantId);

  void deleteParticipantByParticipantId(Long participantId);

  Participant findParticipantByUser_UserIdAndEvent_EventId(Long userId, Long eventId);
}
