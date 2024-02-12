package com.mhc.backend.service;

import com.mhc.backend.dto.EventTypeConvertor;
import com.mhc.backend.dto.EventTypeDto;
import com.mhc.backend.model.User;
import com.mhc.backend.repository.EventTypeRepository;
import com.mhc.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventTypeServiceImpl implements EventTypeService {

    private final EventTypeRepository eventTypeRepository;
    private final EventTypeConvertor eventTypeConvertor;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(EventTypeServiceImpl.class);

    /**
     * Creates a new event type based on the provided EventTypeDto.
     *
     * @param event The EventTypeDto containing information for creating the new event type.
     * @return ResponseEntity with a success message if the event type is created successfully,
     *         an error response with internal server error if an exception occurs.
     */
    @Transactional
    @Override
    public ResponseEntity<String> createEventType(EventTypeDto event) {
        try {
            User vendor = userRepository.findById(event.getVendorId()).orElseThrow();
            eventTypeRepository.save(eventTypeConvertor.toEventTypeEntity(event, vendor));
            logger.info("EventType saved successfully.");
            return ResponseEntity.ok("EventType saved successfully.");
        } catch (Exception e) {
            logger.error("Error saving the EventType", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving the EventType: " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of all event types.
     *
     * @return ResponseEntity containing a list of EventTypeDto if successful,
     *         or an error response with internal server error if an exception occurs.
     */
    @Transactional
    @Override
    public ResponseEntity<List<EventTypeDto>> getAllEventTypes() {
        try {
            List<EventTypeDto> eventTypeDtos = eventTypeRepository.findAll().stream()
                    .map(eventTypeConvertor::toEventTypeDto)
                    .collect(Collectors.toList());
            logger.info("Retrieved all EventTypes successfully.");
            return ResponseEntity.ok(eventTypeDtos);
        } catch (Exception e) {
            logger.error("Error retrieving all EventTypes", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
