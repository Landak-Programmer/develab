package com.landak.develab.entity;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public class ParkingEntity extends IEntity<String> {

    @Id
    private String carPackNo;

    @JsonIgnore
    private LocalDateTime dateCreated = LocalDateTime.now();

    @JsonIgnore
    private LocalDateTime lastUpdated = LocalDateTime.now();

    @Override
    public String getID() {
        return carPackNo;
    }

}
