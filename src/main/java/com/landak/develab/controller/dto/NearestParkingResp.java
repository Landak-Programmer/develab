package com.landak.develab.controller.dto;

public record NearestParkingResp(String address, double latitude, double longitude, int totalLots, int availableLots) {

}
