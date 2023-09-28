package com.landak.develab.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "parking_availability")
@EqualsAndHashCode(callSuper = true)
public class ParkingAvailability extends ParkingEntity {

    @Column(nullable = false)
    private Integer totalLots;

    @Column(nullable = false)
    private Integer lotsAvailable;

}
