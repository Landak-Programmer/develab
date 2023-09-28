package com.landak.develab.cron;

import com.landak.develab.config.IntegrationConfig;
import com.landak.develab.http.HttpRequest;
import com.landak.develab.http.HttpService;
import com.landak.develab.obj.CarParkAvail;
import com.landak.develab.obj.CarParkAvailData;
import com.landak.develab.obj.CarParkAvailResp;
import com.landak.develab.service.ParkingAvailabilityService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@ConditionalOnProperty(
        value = {
                "app.scheduling.parking_availability.enabled"
        },
        havingValue = "true")
@Component
public class ParkingAvailabilityCron {

    private final ParkingAvailabilityService service;
    private final HttpService httpService;
    private final IntegrationConfig config;

    public ParkingAvailabilityCron(
            final ParkingAvailabilityService service,
            final HttpService httpService,
            final IntegrationConfig config) {
        this.service = service;
        this.httpService = httpService;
        this.config = config;
    }

    @Scheduled(cron = "${app.scheduling.parking_availability.cron_expression}")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cron() {
        final String url = String.format("%s/v1/transport/carpark-availability", config.getDataGovSg());
        final HttpRequest httpRequest = new HttpRequest(url, HttpMethod.GET);
        final CarParkAvailResp resp = httpService.execute(httpRequest, CarParkAvailResp.class);
        for (final CarParkAvail c : resp.getItems()) {
            for (final CarParkAvailData cd : c.getCarparkData()) {
            }
        }
    }

}
