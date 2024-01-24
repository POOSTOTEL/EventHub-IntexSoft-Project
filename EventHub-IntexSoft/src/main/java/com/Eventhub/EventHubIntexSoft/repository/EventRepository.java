package com.Eventhub.EventHubIntexSoft.repository;

import com.Eventhub.EventHubIntexSoft.entity.Event;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
  Event findEventByEventId(Long id);

  void deleteEventByEventId(Long id);

  List<Event> findAllByTitle(String title);
}
