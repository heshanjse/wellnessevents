package com.mhc.backend.controller;

import com.mhc.backend.config.JwtService;
import com.mhc.backend.dto.EventDto;
import com.mhc.backend.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/human-resource")
public class HumanResourceController {

    private final EventService eventService;

    @GetMapping("/events")
    @PreAuthorize("hasAuthority('hrAdmin:read')")
    public ResponseEntity<List<EventDto>> getEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/event/{role}/{company}")
    @PreAuthorize("hasAnyAuthority('hrAdmin:read','hrUser:read')")
    public ResponseEntity<List<EventDto>> getUserEvent(@PathVariable String role,@PathVariable String company) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return eventService.getUserEvents(role,company);
    }

    @PostMapping("/event")
    @PreAuthorize("hasAnyAuthority('hrAdmin:create','hrUser:create')")
    public ResponseEntity<EventDto> addEvents(@RequestBody EventDto event) {
        return eventService.createEvent(event);

    }
}
