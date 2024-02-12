package com.mhc.backend.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_type_id")
    private Long eventTypeId;

    @Column(name = "event_name")
    private String eventName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
