package com.landak.develab.builder;

import java.util.ArrayList;
import java.util.List;
import com.landak.develab.entity.ParkingAvailability;
import com.landak.develab.entity.ParkingAvailabilityInfo;

public class ParkingAvailabilityBuilder {

    private String carParkNo;
    private final List<ParkingAvailabilityInfo> parkingInfo = new ArrayList<>();

    public ParkingAvailability build() {
        final ParkingAvailability parkingAvailability = new ParkingAvailability();
        parkingAvailability.setCarParkNo(this.carParkNo);
        parkingAvailability.setInfo(parkingInfo);
        return parkingAvailability;
    }

    public ParkingAvailabilityBuilder setCarParkNo(final String carParkNo) {
        this.carParkNo = carParkNo;
        return this;
    }

    public ParkingAvailabilityBuilder setInfo(final int totalLots, final int lotsAvailable) {
        final ParkingAvailabilityInfo info = new ParkingAvailabilityInfo();
        info.setTotalLots(totalLots);
        info.setLotsAvailable(lotsAvailable);
        parkingInfo.add(info);
        return this;
    }

}
