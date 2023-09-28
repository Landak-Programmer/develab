package com.landak.develab.service.impl;

import java.util.List;
import com.landak.develab.entity.ParkingAvailability;
import com.landak.develab.repo.ParkingAvailabilityRepo;
import com.landak.develab.service.BaseEntityService;
import com.landak.develab.service.ParkingAvailabilityService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ParkingAvailabilityServiceImpl extends BaseEntityService<ParkingAvailability, String> implements ParkingAvailabilityService {

    private final ParkingAvailabilityRepo repo;

    public ParkingAvailabilityServiceImpl(final ParkingAvailabilityRepo repo) {
        this.repo = repo;
    }

    @Override
    protected JpaRepository<ParkingAvailability, String> getRepository() {
        return repo;
    }
    
    @Override
    public final void updateParkingAvailability(final List<ParkingAvailability> list) {
        repo.deleteAll();
        repo.saveAll(list);
    }

}
