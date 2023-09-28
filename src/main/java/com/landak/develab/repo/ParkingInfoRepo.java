package com.landak.develab.repo;

import com.landak.develab.entity.ParkingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingInfoRepo extends JpaRepository<ParkingInfo, String> {

}
