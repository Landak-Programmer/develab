package com.landak.develab.util.geo;

public record SVY21Coordinate(double northing, double easting) {

    public LatLonCoordinate asLatLon() {
        return SVY21.computeLatLon(this);
    }

}
