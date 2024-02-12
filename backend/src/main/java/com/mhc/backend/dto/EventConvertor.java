package com.mhc.backend.dto;


import com.mhc.backend.model.Event;
import com.mhc.backend.model.EventType;
import com.mhc.backend.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class EventConvertor {
    public EventDto toEventDto(Event event) {
        return EventDto.builder()
                .eventId(event.getEventId())
                .eventTypeId(event.getEventType().getEventTypeId())
                .eventName(event.getEventType().getEventName())
                .confirmedDate(event.getConfirmedDate())
                .location(event.getLocation())
                .status(event.getStatus())
                .remarks(event.getRemarks())
                .createdDate(event.getCreatedDate().toString())
                .humanResourceId(event.getUser().getUserId())
                .humanResourceName(event.getUser().getCompanyName())
                .vendorId(event.getEventType().getUser().getUserId())
                .vendorName(event.getEventType().getUser().getCompanyName())
                .build();
    }

    public Event toEventEntity(EventDto eventDto, User humanResource, EventType eventType) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return Event.builder()
                .eventId(eventDto.getEventId())
                .eventType(eventType)
                .confirmedDate(eventDto.getConfirmedDate())
                .location(eventDto.getLocation())
                .status(eventDto.getStatus())
                .remarks(eventDto.getRemarks())
                .createdDate(LocalDate.parse(eventDto.getCreatedDate(), formatter).atStartOfDay())
                .user(humanResource)
                .build();
    }

    public User toVendorUserEntity(EventDto eventDto){
        return User.builder()
                .userId(eventDto.getVendorId())
                .companyName(eventDto.getVendorName())
                .build();
    }

    public EventType toEventTypeEntity(EventDto eventDto, User vendor) {
        return EventType.builder()
                .eventTypeId(eventDto.getEventTypeId())
                .eventName(eventDto.getEventName())
                .user(vendor)
                .build();
    }

    public User toHumanResourceUserEntity(EventDto eventDto){
        return User.builder()
                .userId(eventDto.getHumanResourceId())
                .companyName(eventDto.getHumanResourceName())
                .build();
    }


}
