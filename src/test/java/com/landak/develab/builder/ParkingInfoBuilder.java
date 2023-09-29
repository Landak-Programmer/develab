package com.landak.develab.builder;

import com.landak.develab.entity.ParkingInfo;

public class ParkingInfoBuilder {

    private String carParkNo;
    private String address;
    private double latitude;
    private double longitude;

    public ParkingInfo build() {
        final ParkingInfo parkingInfo = new ParkingInfo();
        parkingInfo.setCarParkNo(this.carParkNo);
        parkingInfo.setAddress(this.address);
        parkingInfo.setLatitude(this.latitude);
        parkingInfo.setLongitude(this.longitude);
        return parkingInfo;
    }

    public ParkingInfoBuilder setCarParkNo(final String carParkNo) {
        this.carParkNo = carParkNo;
        return this;
    }
    
    public ParkingInfoBuilder setAddress(final String address) {
        this.address = address;
        return this;
    }

    public ParkingInfoBuilder setLatitude(final double latitude) {
        this.latitude = latitude;
        return this;
    }

    public ParkingInfoBuilder setLongitude(final double longitude) {
        this.longitude = longitude;
        return this;
    }
}
