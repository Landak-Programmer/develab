package com.landak.develab.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;

public abstract class IEntity<I extends Serializable> implements Serializable {

    @JsonIgnore
    private LocalDateTime dateCreated = LocalDateTime.now();

    @JsonIgnore
    private LocalDateTime lastUpdated = LocalDateTime.now();

    public abstract I getID();

}
