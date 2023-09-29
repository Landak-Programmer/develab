package com.landak.develab.builder;

import com.landak.develab.builder.obj.ParkingTestObj;
import com.landak.develab.entity.ParkingAvailability;
import com.landak.develab.entity.ParkingInfo;

public class ParkingBuilder {
    
    public ParkingTestObj autoBuild(final String carParkNo, final double latitude, final double longitude, 
                                    final int totalLots, final int lotsAvailable) {
        final ParkingInfo parkingInfo = new ParkingInfoBuilder()
                .setCarParkNo(carParkNo)
                .setAddress(carParkNo)
                .setLatitude(latitude)
                .setLongitude(longitude)
                .build();
        
        final ParkingAvailability parkingAvailability = new ParkingAvailabilityBuilder()
                .setCarParkNo(carParkNo)
                .setInfo(totalLots, lotsAvailable)
                .build();
        
        return new ParkingTestObj(parkingInfo, parkingAvailability);
    }
}
