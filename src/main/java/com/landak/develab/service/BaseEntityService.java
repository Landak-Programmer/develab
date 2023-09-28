package com.landak.develab.service;

import java.io.Serializable;
import com.landak.develab.entity.IEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseEntityService<E extends IEntity<I>, I extends Serializable> {

    protected abstract JpaRepository<E, I> getRepository();
}
