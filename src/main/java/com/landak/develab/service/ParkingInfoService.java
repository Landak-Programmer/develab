package com.landak.develab.service;

import java.util.List;
import com.landak.develab.controller.dto.NearestParkingResp;

public interface ParkingInfoService {

    List<NearestParkingResp> getNearestCarPark(
            double latitude, double longitude,
            int page, double perPage);

}
