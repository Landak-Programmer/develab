package com.landak.develab.controller;

import java.util.List;
import com.landak.develab.controller.dto.NearestParkingResp;
import com.landak.develab.service.ParkingInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController("/carparks")
public class ParkingController {

    private final ParkingInfoService service;

    public ParkingController(final ParkingInfoService service) {
        this.service = service;
    }

    @GetMapping(value = {
            "/nearest"
    }, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NearestParkingResp>> nearestCarPark(
            @RequestParam double latitude, @RequestParam double longitude,
            @RequestParam(required = false) int page, @RequestParam(required = false) double perPage) {
        return ResponseEntity.ok(service.getNearestCarPark(latitude, longitude, page, perPage));
    }

}
