package com.mhc.backend.dto;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder
public class EventTypeDto {

    private Long eventTypeId;
    private String eventName;
    private Long vendorId;
    private String vendorName;

}
