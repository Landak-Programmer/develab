package com.landak.develab.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ParkingAvailabilityInfo {

    @Column(nullable = false)
    private Integer totalLots;

    @Column(nullable = false)
    private Integer lotsAvailable;

}
