package com.landak.develab.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.landak.develab.controller.dto.NearestParkingResp;
import com.landak.develab.entity.ParkingInfo;
import com.landak.develab.repo.ParkingInfoRepo;
import com.landak.develab.service.BaseEntityService;
import com.landak.develab.service.ParkingInfoService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

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
            int page, double perPage) {
        final List<NearestParkingResp> resp = new ArrayList<>();
        return resp;
    }

}
