package com.mhc.backend.service;

import com.mhc.backend.dto.EventTypeDto;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface EventTypeService {

    ResponseEntity<String> createEventType(EventTypeDto event);

    ResponseEntity<List<EventTypeDto>> getAllEventTypes();
}
