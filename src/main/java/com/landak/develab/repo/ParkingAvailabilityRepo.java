package com.landak.develab.repo;

import com.landak.develab.entity.ParkingAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingAvailabilityRepo extends JpaRepository<ParkingAvailability, String> {

}
