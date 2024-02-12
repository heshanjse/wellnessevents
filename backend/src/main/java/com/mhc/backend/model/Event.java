package com.mhc.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @ManyToOne
    @JoinColumn(name = "event_type_id")
    private EventType eventType;

    @Column(name = "confirmed_date")
    private String confirmedDate;

    // use date option list , use type datatime

    @Column(name = "location")
    private String location;

    @Column(name = "status")
    private String status; // use enum status

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
