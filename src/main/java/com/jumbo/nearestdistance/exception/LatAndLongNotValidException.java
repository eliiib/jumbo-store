package com.jumbo.nearestdistance.exception;

public class LatAndLongNotValidException extends RuntimeException {

    public LatAndLongNotValidException(String lon, String lat) {
        super(new StringBuilder().append("Latitude: ").append(lat).append(" or Longitude: ")
                .append(lon).append(" is not valid").toString());
    }
}
