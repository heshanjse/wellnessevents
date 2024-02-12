package com.mhc.backend.service;

import com.mhc.backend.dto.EventConvertor;
import com.mhc.backend.dto.EventDto;
import com.mhc.backend.dto.EventTypeConvertor;
import com.mhc.backend.dto.EventTypeDto;
import com.mhc.backend.model.Event;
import com.mhc.backend.model.EventType;
import com.mhc.backend.model.User;
import com.mhc.backend.repository.EventRepository;
import com.mhc.backend.repository.EventTypeRepository;
import com.mhc.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EventTypeServiceImplTest {



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
    void testCreateEventType() {

        var eventTypeConverter = new EventTypeConvertor();
        var eventTypeServiceImpl = new EventTypeServiceImpl(eventTypeRepository, eventTypeConverter,userRepository);
        var user = createUser();
        var eventType = createEventType();
        var eventTypeDto = createEventTypeDto();

        when(userRepository.findById(eventTypeDto.getVendorId())).thenReturn(java.util.Optional.ofNullable(user));
        when(eventTypeRepository.save(eventType)).thenReturn(eventType);
        var eventTypeRes = eventTypeServiceImpl.createEventType(eventTypeDto);

        var status = eventTypeRes.getStatusCode();
        assertEquals(200, status.value());

    }

    @Test
    public void testCreateEventTypeFailure() {

        var eventTypeConverter = new EventTypeConvertor();
        var eventTypeServiceImpl = new EventTypeServiceImpl(eventTypeRepository, eventTypeConverter,userRepository);
        var user = createUser();
        var eventType = createEventType();
        var eventTypeDto = createEventTypeDto();

        when(userRepository.findById(eventTypeDto.getVendorId())).thenReturn(java.util.Optional.ofNullable(null));
        when(eventTypeRepository.save(eventType)).thenReturn(eventType);
        var eventTypeRes = eventTypeServiceImpl.createEventType(eventTypeDto);

        var status = eventTypeRes.getStatusCode();
        assertEquals(500, status.value());

    }

    @Test
    void testGetAllEventTypes() {

        var eventTypeConverter = new EventTypeConvertor();
        var eventTypeServiceImpl = new EventTypeServiceImpl(eventTypeRepository, eventTypeConverter,userRepository);
        var eventType = createEventType();

        when(eventTypeRepository.findAll()).thenReturn(List.of(eventType));
        var eventTypes = eventTypeServiceImpl.getAllEventTypes();

        List<EventTypeDto> body = eventTypes.getBody();
        assertEquals(1, body.size());
        assertEquals(1L, body.get(0).getEventTypeId());
    }

    @Test
    public void testGetAllEventTypesFailure() {

        var eventTypeConverter = new EventTypeConvertor();
        var eventTypeServiceImpl = new EventTypeServiceImpl(eventTypeRepository, eventTypeConverter,userRepository);

        when(eventTypeRepository.findAll()).thenReturn(null);
        var eventTypes = eventTypeServiceImpl.getAllEventTypes();

        var status = eventTypes.getStatusCode();
        assertEquals(500, status.value());

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