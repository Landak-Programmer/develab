package com.landak.develab.obj;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class CarParkAvailResp {

    private List<CarParkAvail> items = new ArrayList<>();

}
