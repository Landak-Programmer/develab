package com.landak.develab.service.impl;

import com.landak.develab.controller.dto.NearestParkingResp;
import com.landak.develab.controller.dto.Pageable;
import com.landak.develab.entity.ParkingInfo;
import com.landak.develab.repo.ParkingInfoRepo;
import com.landak.develab.service.BaseEntityService;
import com.landak.develab.service.ParkingInfoService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingInfoServiceImpl extends BaseEntityService<ParkingInfo, String> implements ParkingInfoService {

    private final ParkingInfoRepo repo;

    public ParkingInfoServiceImpl(final ParkingInfoRepo repo) {
        this.repo = repo;
    }

    @Override
    protected JpaRepository<ParkingInfo, String> getRepository() {
        return repo;
    }

    @Override
    public List<NearestParkingResp> getNearestCarPark(
            double latitude, double longitude,
            Pageable pageable) {
        final List<Object[]> datas = repo.findNearestLocationByLatLon(
                latitude, longitude, pageable.pageSize(), pageable.getOffset());
        final List<NearestParkingResp> result = new ArrayList<>();
        for (final Object[] data : datas) {
            final NearestParkingResp r = new NearestParkingResp(
                    (String) data[0], ((BigDecimal) data[1]).doubleValue(), ((BigDecimal) data[2]).doubleValue(),
                    (Integer) data[3], (Integer) data[4]
            );
            result.add(r);
        }
        return result;
    }

}
