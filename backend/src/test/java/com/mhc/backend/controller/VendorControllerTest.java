package com.mhc.backend.controller;

import com.mhc.backend.dto.EventDto;
import com.mhc.backend.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VendorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService service;

    @Test
    void getEvents() {
//        EventDto eventdto = createEventDto();
//        when(service.getAllEvents()).thenReturn((ResponseEntity<List<EventDto>>) List.of(eventdto));
//        this.mockMvc.perform(get("/events")).andDo(print()).andExpect(status().isOk());
        // need to mock spring security classes
    }

    @Test
    void addEvents() {
    }

    private EventDto createEventDto() {
        return EventDto.builder()
                .eventId(1L)
                .eventTypeId(1L)
                .eventName("Health event")
                .confirmedDate("")
                .location("")
                .status("")
                .remarks("")
                .createdDate("2023-02-23")
                .humanResourceId(1L)
                .humanResourceName("")
                .vendorId(1L)
                .vendorName("")
                .build();
    }
}