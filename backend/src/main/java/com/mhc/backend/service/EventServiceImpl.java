package com.mhc.backend.service;

import com.mhc.backend.dto.EventConvertor;
import com.mhc.backend.dto.EventDto;
import com.mhc.backend.model.Event;
import com.mhc.backend.model.EventType;
import com.mhc.backend.model.Role;
import com.mhc.backend.model.User;
import com.mhc.backend.repository.*;
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
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventTypeRepository eventTypeRepository;
    private final UserRepository userRepository;
    private final EventConvertor eventConvertor;
    private static final Logger logger = LoggerFactory.getLogger(EventTypeServiceImpl.class);

    /**
     * Creates a new event based on the provided EventDto.
     *
     * @param eventDto The EventDto object containing the details of the event to be created.
     * @return ResponseEntity containing the created EventDto if the operation is successful,
     *         or an error response with internal server error if an exception occurs.
     */
    @Transactional
    @Override
    public ResponseEntity<EventDto> createEvent(EventDto eventDto) {
        try {
            EventType eventType = eventTypeRepository.findById(eventDto.getEventTypeId()).orElseThrow();
            User humanResource = userRepository.findById(eventDto.getHumanResourceId()).orElseThrow();
            eventRepository.save(eventConvertor.toEventEntity(eventDto, humanResource, eventType));
            logger.info("Event saved successfully.");
            return ResponseEntity.ok(eventDto);
        } catch (Exception e) {
            logger.error("Error saving the Event", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Updates an existing event based on the provided EventDto.
     *
     * @param eventDto The EventDto object containing the updated details of the event.
     * @return ResponseEntity containing the updated EventDto if the operation is successful,
     *         or an error response with internal server error if an exception occurs.
     */
    @Transactional
    @Override
    public ResponseEntity<EventDto> updateEvent(EventDto eventDto) {
        try {
            Event event = eventRepository.findById(eventDto.getEventId()).orElseThrow();
            event.setStatus(eventDto.getStatus());
            event.setRemarks(eventDto.getRemarks());
            event.setConfirmedDate(eventDto.getConfirmedDate());
            eventRepository.save(event);
            logger.info("Event update successfully.");
            return ResponseEntity.ok(eventConvertor.toEventDto(event));
        } catch (Exception e) {
            logger.error("Error updating the Event", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Retrieves all events.
     *
     * @return ResponseEntity containing a list of EventDto objects if the retrieval is successful,
     *         or an error response with intenal server error if an exception occurs.
     */
    @Transactional
    @Override
    public ResponseEntity<List<EventDto>> getAllEvents() {
        try {
            List<EventDto> eventDtos = eventRepository.findAll().stream()
                    .map(eventConvertor::toEventDto)
                    .collect(Collectors.toList());
            logger.info("Retrieved all Event successfully.");
            return ResponseEntity.ok(eventDtos);
        } catch (Exception e) {
            logger.error("Error retrieving the Event", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Retrieves events based on the user's role and company name.
     *
     * @param role    The user's role "HR_USER" , "VENDOR_USER" etc.
     * @param company The name of the company associated with the user.
     * @return ResponseEntity containing a list of EventDto objects if the retrieval is successful,
     *         or an error response with internal server error if an exception occurs.
     */
    @Transactional
    @Override
    public ResponseEntity<List<EventDto>> getUserEvents(String role,String company) {
        try {
            List<EventDto> eventDtos;
            if (Role.HR_USER.toString().equals(role)) {
                System.out.println(Role.HR_USER.equals(role));
                List<User> hrUsers = userRepository.findByCompanyName(company);
                eventDtos = eventRepository.findByUserIn(hrUsers).stream()
                        .map(eventConvertor::toEventDto)
                        .collect(Collectors.toList());
            } else if (Role.VENDOR_USER.toString().equals(role)) {
                List<User> vendorUsers = userRepository.findByCompanyName(company);
                List<EventType> eventTypes = eventTypeRepository.findByUserIn(vendorUsers);
                eventDtos = eventRepository.findByEventTypeIn(eventTypes).stream()
                        .map(eventConvertor::toEventDto)
                        .collect(Collectors.toList());

            } else {
                eventDtos = eventRepository.findAll().stream()
                        .map(eventConvertor::toEventDto)
                        .collect(Collectors.toList());
            }
              logger.info("Retrieved User Event successfully.");
            return ResponseEntity.ok(eventDtos);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
