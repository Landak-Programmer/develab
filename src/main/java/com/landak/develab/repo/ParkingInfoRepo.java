package com.landak.develab.repo;

import com.landak.develab.controller.dto.NearestParkingResp;
import com.landak.develab.entity.ParkingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingInfoRepo extends JpaRepository<ParkingInfo, String> {

    @Query(value = """
            SELECT
                pi.address,
                pi.latitude,
                pi.longitude,
                pai.total_lots,
                pai.lots_available,
                (
                    6371 *\s
                    acos(
                        cos(radians(?1)) * cos(radians(latitude)) *\s
                        cos(radians(longitude) - radians(?2)) +\s
                        sin(radians(?1)) * sin(radians(latitude))
                    )
                ) AS distance
            FROM
                parking_info pi
            LEFT JOIN parking_availability pa ON pa.car_park_no = pi.car_park_no
            LEFT JOIN parking_availability_info pai ON pai.parking_availability_car_park_no = pa.car_park_no
            WHERE pai.lots_available > 0
            ORDER BY distance
            LIMIT ?3 OFFSET ?4
            """, nativeQuery = true)
    List<Object[]> findNearestLocationByLatLon(double latitude, double longitude, int limit, int offset);
}
