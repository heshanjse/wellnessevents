package com.mhc.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@AllArgsConstructor
@Builder
public class EventDto {

    private Long eventId;
    private Long eventTypeId;
    private String eventName;
    private String confirmedDate;
    private String location;
    private String status;
    private String remarks;
    private String createdDate; //covert to datetime
    private Long humanResourceId;
    private String humanResourceName;
    private Long vendorId;
    private String vendorName;

}
