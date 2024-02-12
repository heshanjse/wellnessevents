package com.mhc.backend.repository;

import com.mhc.backend.model.EventType;
import com.mhc.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType,Long> {

    List<EventType> findByUserIn(Collection<User> users);
}
