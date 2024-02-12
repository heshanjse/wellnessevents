package com.mhc.backend.service;

import com.mhc.backend.dto.EventDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EventService {

    ResponseEntity<EventDto> createEvent(EventDto event);

    ResponseEntity<EventDto> updateEvent(EventDto event);

    ResponseEntity<List<EventDto>> getAllEvents();

    ResponseEntity<List<EventDto>> getUserEvents(String role,String company);
}
