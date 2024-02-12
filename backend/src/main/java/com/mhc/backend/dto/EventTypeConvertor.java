package com.mhc.backend.dto;

import com.mhc.backend.model.EventType;
import com.mhc.backend.model.User;
import org.springframework.stereotype.Component;

@Component
public class EventTypeConvertor {

    public EventTypeDto toEventTypeDto(EventType eventType) {
        return EventTypeDto.builder()
                .eventTypeId(eventType.getEventTypeId())
                .eventName(eventType.getEventName())
                .build();
    }

    public EventType toEventTypeEntity(EventTypeDto eventTypeDto, User vendor) {
        return EventType.builder()
                .eventTypeId(eventTypeDto.getEventTypeId())
                .eventName(eventTypeDto.getEventName())
                .user(vendor)
                .build();
    }

    public User toUserEntity(EventTypeDto eventTypeDto){
        return User.builder()
                .userId(eventTypeDto.getVendorId())
                .companyName(eventTypeDto.getVendorName())
                .build();
    }

}
