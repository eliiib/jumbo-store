package com.jumbo.nearestdistance.util;

import com.jumbo.nearestdistance.exception.LatAndLongNotValidException;
import org.springframework.data.geo.Point;

public class ValidationUtil {

    /**
     * Valid longitude values are between -180 and 180, both inclusive.
     * Valid latitude values are between -90 and 90, both inclusive.
     */
    public static void validateCoordinate(Point point) {
        if (point.getX() > 180 || point.getX() < -180 || point.getY() > 90 || point.getY() < -90)
            throw new LatAndLongNotValidException(String.valueOf(point.getX()), String.valueOf(point.getY()));
    }
}
