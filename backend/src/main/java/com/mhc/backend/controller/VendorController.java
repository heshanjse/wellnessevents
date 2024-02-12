package com.mhc.backend.controller;



import com.mhc.backend.dto.EventDto;
import com.mhc.backend.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/vendor")
@RequiredArgsConstructor
public class VendorController {

    private final EventService eventService;

    @GetMapping("/events")
    @PreAuthorize("hasAuthority('vendorAdmin:read')")
    public ResponseEntity<List<EventDto>> getEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/event/{role}/{company}")
    @PreAuthorize("hasAnyAuthority('vendorAdmin:read','vendorUser:read')")
    public ResponseEntity<List<EventDto>> getEvent(@PathVariable String role,@PathVariable String company) {
        return eventService.getUserEvents(role,company);
    }

    @PostMapping("/event")
    @PreAuthorize("hasAnyAuthority('vendorAdmin:update','vendorUser:update')")
    public ResponseEntity<EventDto> addEvents(@RequestBody EventDto event) {
        return eventService.updateEvent(event);

    }
}
