package com.landak.develab.util.hdb.obj;

public record HDBRecord(String carParkNo,
                        String address,
                        double coorX,
                        double coorY,
                        String type,
                        String systemType,
                        String term,
                        String free,
                        String nightPark,
                        int deck,
                        double height,
                        boolean basement) {
}
