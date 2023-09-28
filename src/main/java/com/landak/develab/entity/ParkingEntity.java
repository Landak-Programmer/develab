package com.landak.develab.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public class ParkingEntity extends IEntity<String> {

    @Id
    @Column(name = "car_park_no")
    private String carParkNo;

    @Override
    public String getID() {
        return carParkNo;
    }

}
