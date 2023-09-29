package com.landak.develab.service;

import java.util.List;
import com.landak.develab.controller.dto.NearestParkingResp;
import com.landak.develab.controller.dto.Pageable;

public interface ParkingInfoService {

    List<NearestParkingResp> getNearestCarPark(
            double latitude, double longitude,
            Pageable pageable);

}
