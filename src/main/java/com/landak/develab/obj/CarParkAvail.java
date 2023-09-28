package com.landak.develab.obj;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CarParkAvail {

    @SerializedName("carpark_data")
    private List<CarParkAvailData> carparkData = new ArrayList<>();

}
