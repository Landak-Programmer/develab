package com.landak.develab.obj;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CarParkAvailData {

    @SerializedName("carpark_number")
    private String carparkNumber;
    @SerializedName("carpark_info")
    private List<CarParkAvailInfo> carparkInfo = new ArrayList<>();

}
