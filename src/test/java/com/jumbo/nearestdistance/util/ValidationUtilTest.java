package com.jumbo.nearestdistance.util;

import com.jumbo.nearestdistance.exception.LatAndLongNotValidException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
public class ValidationUtilTest {

    @Test
    public void testValidateCoordinate_invalidLat_throwException() {
        Point point = new Point(98, 120);
        Assertions.assertThatExceptionOfType(LatAndLongNotValidException.class).isThrownBy(
                () -> ValidationUtil.validateCoordinate(point));
    }

    @Test
    public void testValidateCoordinate_invalidLon_throwException() {
        Point point = new Point(183.001, 89.004);
        Assertions.assertThatExceptionOfType(LatAndLongNotValidException.class).isThrownBy(
                () -> ValidationUtil.validateCoordinate(point));
    }

    @Test
    public void testValidateCoordinate_invalidLatAndLon_throwException() {
        Point point = new Point(183.001, 95.004);
        Assertions.assertThatExceptionOfType(LatAndLongNotValidException.class).isThrownBy(
                () -> ValidationUtil.validateCoordinate(point));
    }

    @Test
    public void testValidateCoordinate_validLatAndLon_throwException() {
        Point point = new Point(150.001, 46.004);
        ValidationUtil.validateCoordinate(point);
    }
}
