package com.mhc.backend.service;

import com.mhc.backend.dto.EventConvertor;
import com.mhc.backend.dto.EventDto;
import com.mhc.backend.dto.EventTypeDto;
import com.mhc.backend.model.Event;
import com.mhc.backend.model.EventType;
import com.mhc.backend.model.User;
import com.mhc.backend.repository.EventRepository;
import com.mhc.backend.repository.EventTypeRepository;
import com.mhc.backend.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EventServiceImplTest {

    private EventRepository eventRepository;
    private EventTypeRepository eventTypeRepository;
    private UserRepository userRepository;
    private LocalDateTime now;
    private Long id;
    private String eventName;
    private String confirmdates;


    @BeforeEach
    public void setUp() {
        eventRepository = mock(EventRepository.class);
        eventTypeRepository = mock(EventTypeRepository.class);
        userRepository = mock(UserRepository.class);
        now = LocalDateTime.now();
        id = 1L;
        eventName= "Health event";
        confirmdates= "2023-02-02,2023-03-04";
    }

    @Test
    void testCreateEvent() {
        var eventConverter = new EventConvertor();
        var eventServiceImpl = new EventServiceImpl(eventRepository, eventTypeRepository, userRepository, eventConverter);
        var eventdto = createEventDto();
        var event = createEvent();
        var user = createUser();
        var eventType = createEventType();
        when(eventTypeRepository.findById(createEventTypeDto().getEventTypeId())).thenReturn(java.util.Optional.ofNullable(eventType));
        when(userRepository.findById(user.getUserId())).thenReturn(java.util.Optional.ofNullable(user));
        when(eventRepository.save(event)).thenReturn(event);
        var events = eventServiceImpl.createEvent(eventdto);

        EventDto edto = events.getBody();
        assertEquals(1, edto.getEventId());

    }

    @Test
    void testCreateEventFailure() {

        var eventConverter = new EventConvertor();
        var eventServiceImpl = new EventServiceImpl(eventRepository, eventTypeRepository, userRepository, eventConverter);
        var eventdto = createEventDto();
        var event = createEvent();
        var user = createUser();
        var eventType = createEventType();
        when(eventTypeRepository.findById(createEventTypeDto().getEventTypeId())).thenReturn(null);
        when(userRepository.findById(user.getUserId())).thenReturn(java.util.Optional.ofNullable(user));
        when(eventRepository.save(event)).thenReturn(event);
        var events = eventServiceImpl.createEvent(eventdto);

        var status = events.getStatusCode();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, status);

    }



    @Test
    void testUpdateEvent() {

        var eventConverter = new EventConvertor();
        var eventServiceImpl = new EventServiceImpl(eventRepository, eventTypeRepository, userRepository, eventConverter);
        var eventdto = createEventDto();
        var event = createEvent();
        when(eventRepository.findById(eventdto.getEventId())).thenReturn(java.util.Optional.ofNullable(event));
        when(eventRepository.save(event)).thenReturn(event);
        var events = eventServiceImpl.updateEvent(eventdto);

        EventDto edto = events.getBody();
        assertEquals(1, edto.getEventId());

    }

    @Test
    void testUpdateEventFailure() {
        var eventConverter = new EventConvertor();
        var eventServiceImpl = new EventServiceImpl(eventRepository, eventTypeRepository, userRepository, eventConverter);
        var eventdto = createEventDto();
        var event = createEvent();
        when(eventRepository.findById(eventdto.getEventId())).thenReturn(null);
        when(eventRepository.save(event)).thenReturn(event);
        var events = eventServiceImpl.updateEvent(eventdto);

        var status = events.getStatusCode();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, status);

    }

    @Test
    void testGetAllEvents() {
        var eventConverter = new EventConvertor();
        var eventServiceImpl = new EventServiceImpl(eventRepository, eventTypeRepository, userRepository, eventConverter);
        var event = createEvent();
        when(eventRepository.findAll()).thenReturn(List.of(event));
        var events = eventServiceImpl.getAllEvents();

        List<EventDto> body = events.getBody();
        assertEquals(1, body.size());
        assertEquals(1L, body.get(0).getEventId());
    }

    @Test
    void testGetAllEventsFailure() {
        var eventConverter = new EventConvertor();
        var eventServiceImpl = new EventServiceImpl(eventRepository, eventTypeRepository, userRepository, eventConverter);
        var event = createEvent();
        when(eventRepository.findAll()).thenReturn(null);
        var events = eventServiceImpl.getAllEvents();
        var status = events.getStatusCode();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, status);
    }

    private EventDto createEventDto() {
        return EventDto.builder()
                .eventId(id)
                .eventTypeId(id)
                .eventName(eventName)
                .confirmedDate(confirmdates)
                .location("")
                .status("")
                .remarks("")
                .createdDate("2023-02-23")
                .humanResourceId(id)
                .humanResourceName("")
                .vendorId(id)
                .vendorName("")
                .build();
    }

    private Event createEvent() {
        return Event.builder()
                .eventId(id)
                .eventType(createEventType())
                .confirmedDate(confirmdates)
                .location("")
                .status("")
                .remarks("")
                .createdDate(now)
                .user(createUser())
                .build();
    }

    private EventTypeDto createEventTypeDto() {
        return EventTypeDto.builder()
                .eventTypeId(1L)
                .eventName("eventName")
                .build();
    }

    private User createUser() {
        return User.builder()
                .userId(id)
                .companyName("companyName")
                .build();
    }

    private EventType createEventType() {
        return EventType.builder()
                .eventTypeId(1L)
                .eventName("eventName")
                .user(createUser())
                .build();
    }

}