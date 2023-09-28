package com.landak.develab.util.geo;

public record LatLonCoordinate(double latitude, double longitude) {

    public SVY21Coordinate asSVY21() {
        return SVY21.computeSVY21(this);
    }

}
