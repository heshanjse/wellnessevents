package com.mhc.backend.repository;

import com.mhc.backend.model.Event;
import com.mhc.backend.model.EventType;
import com.mhc.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long>{

    List<Event> findByUserIn(List<User> users);

    List<Event> findByEventTypeIn(List<EventType> eventTypes);

}
