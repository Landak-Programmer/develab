package com.landak.develab.controller;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.landak.develab.controller.dto.NearestParkingResp;
import com.landak.develab.controller.dto.Pageable;
import com.landak.develab.service.ParkingInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class ParkingController {

    private final ParkingInfoService service;

    public ParkingController(final ParkingInfoService service) {
        this.service = service;
    }

    @GetMapping(value = {
            "/carparks/nearest"
    }, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NearestParkingResp>> nearestCarPark(
            @RequestParam double latitude, @RequestParam double longitude,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "0") int per_page) {
        final Pageable pageable = new Pageable(page, per_page);
        return ResponseEntity.ok(service.getNearestCarPark(latitude, longitude, pageable));
    }

}
