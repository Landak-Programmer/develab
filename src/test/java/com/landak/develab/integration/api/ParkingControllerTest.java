package com.landak.develab.integration.api;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.landak.develab.builder.ParkingBuilder;
import com.landak.develab.builder.obj.ParkingTestObj;
import com.landak.develab.controller.dto.NearestParkingResp;
import com.landak.develab.http.HttpRequest;
import com.landak.develab.http.HttpService;
import com.landak.develab.repo.ParkingAvailabilityRepo;
import com.landak.develab.repo.ParkingInfoRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Rollback
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ParkingControllerTest {

    @Autowired
    private ParkingInfoRepo parkingInforepo;
    @Autowired
    private ParkingAvailabilityRepo availableRepo;
    @Autowired
    private HttpService httpService;
    @Autowired
    private Gson gson;

    @LocalServerPort
    private int port;

    final double latitude = 1.3521d;
    final double longitude = 103.8198;
    
    @Before
    public void before() {
        parkingInforepo.deleteAll();
        availableRepo.deleteAll();

        final ParkingTestObj parkingTestObj1 = new ParkingBuilder()
                .autoBuild("A", 1.35783, 103.83163, 1, 1);
        parkingInforepo.save(parkingTestObj1.parkingInfo());
        availableRepo.save(parkingTestObj1.parkingAvailability());

        final ParkingTestObj parkingTestObj2 = new ParkingBuilder()
                .autoBuild("B", 1.35196, 103.83697, 1, 1);
        parkingInforepo.save(parkingTestObj2.parkingInfo());
        availableRepo.save(parkingTestObj2.parkingAvailability());

        final ParkingTestObj parkingTestObj3 = new ParkingBuilder()
                .autoBuild("C", 1.35835, 103.83109, 1, 1);
        parkingInforepo.save(parkingTestObj3.parkingInfo());
        availableRepo.save(parkingTestObj3.parkingAvailability());

        final ParkingTestObj parkingTestObj4 = new ParkingBuilder()
                .autoBuild("D", 1.35835, 103.83109, 1, 0);
        parkingInforepo.save(parkingTestObj4.parkingInfo());
        availableRepo.save(parkingTestObj4.parkingAvailability());
    }
    
    @Test
    public void nearestCarParkTest() {
        final String url = String.format("%s?latitude=%f&longitude=%f", createURLWithPort("/carparks/nearest"), latitude, longitude);
        final HttpRequest request = new HttpRequest(url, HttpMethod.GET);
        final List<NearestParkingResp> result = parseBulkResponse(httpService.execute(request, List.class), NearestParkingResp.class);
        Assert.assertEquals("Size should be 3!", 3, result.size());
    }

    private String createURLWithPort(final String uri) {
        return "http://localhost:" + port + uri;
    }

    protected <T> List<T> parseBulkResponse(final List<?> list, final Class<T> tClass) {
        final List<T> convert = new ArrayList<>();
        list.forEach(i -> convert.add(gson.fromJson(i.toString(), tClass)));
        return convert;
    }
}
