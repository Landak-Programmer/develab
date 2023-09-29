package com.landak.develab.unit.repo;

import java.util.List;
import com.landak.develab.builder.ParkingBuilder;
import com.landak.develab.builder.obj.ParkingTestObj;
import com.landak.develab.repo.ParkingAvailabilityRepo;
import com.landak.develab.repo.ParkingInfoRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ParkingInfoRepoTest {

    final double latitude = 1.3521d;
    final double longitude = 103.8198;

    @Autowired
    private ParkingInfoRepo repo;
    @Autowired
    private ParkingAvailabilityRepo availableRepo;

    @Before
    public void before() {

        repo.deleteAll();
        availableRepo.deleteAll();

        final ParkingTestObj parkingTestObj1 = new ParkingBuilder()
                .autoBuild("A", 1.35783, 103.83163, 1, 1);
        repo.save(parkingTestObj1.parkingInfo());
        availableRepo.save(parkingTestObj1.parkingAvailability());

        final ParkingTestObj parkingTestObj2 = new ParkingBuilder()
                .autoBuild("B", 1.35196, 103.83697, 1, 1);
        repo.save(parkingTestObj2.parkingInfo());
        availableRepo.save(parkingTestObj2.parkingAvailability());

        final ParkingTestObj parkingTestObj3 = new ParkingBuilder()
                .autoBuild("C", 1.35835, 103.83109, 1, 1);
        repo.save(parkingTestObj3.parkingInfo());
        availableRepo.save(parkingTestObj3.parkingAvailability());

        final ParkingTestObj parkingTestObj4 = new ParkingBuilder()
                .autoBuild("D", 1.35835, 103.83109, 1, 0);
        repo.save(parkingTestObj4.parkingInfo());
        availableRepo.save(parkingTestObj4.parkingAvailability());
    }

    @Test
    public void findNearestLocationByLatLonTest() {
        final List<Object[]> result = repo.findNearestLocationByLatLon(latitude, longitude, 10, 0);
        Assert.assertEquals("Size should be 3!", 3, result.size());
        Assert.assertTrue("A should be after C", (double) result.get(0)[5] < (double) result.get(1)[5]);
        Assert.assertTrue("B should be after A", (double) result.get(1)[5] < (double) result.get(2)[5]);
    }

    @Test
    public void findNearestLocationByLatLonTestWithZeroAvailability() {
        final List<Object[]> result = repo.findNearestLocationByLatLon(latitude, longitude, 10, 0);
        Assert.assertEquals("Size should be 3!", 3, result.size());
    }

}
