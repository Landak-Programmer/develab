package com.landak.develab.obj;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CarParkAvailInfo {

    @SerializedName("total_lots")
    private int totalLots;
    @SerializedName("lot_type")
    private String lotType;
    @SerializedName("lots_available")
    private int lotsAvailable;

}
