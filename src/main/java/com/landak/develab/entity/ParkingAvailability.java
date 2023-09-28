package com.landak.develab.entity;

import java.util.List;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "parking_availability")
@EqualsAndHashCode(callSuper = true)
public class ParkingAvailability extends ParkingEntity {

    @ElementCollection
    private List<ParkingAvailabilityInfo> info;

}
