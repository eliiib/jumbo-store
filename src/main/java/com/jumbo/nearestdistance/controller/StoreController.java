package com.jumbo.nearestdistance.controller;


import com.jumbo.nearestdistance.controller.dto.NearestStoresDTO;
import com.jumbo.nearestdistance.exception.BadRequestException;
import com.jumbo.nearestdistance.service.StoreService;
import com.jumbo.nearestdistance.transform.StoreModelMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Point;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;
    private final StoreModelMapper mapper;

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public NearestStoresDTO getNearestList(@RequestParam(value = "lon") String longitude,
                                           @RequestParam(value = "lat") String latitude,
                                           @RequestParam(value = "distance", required = false) Double distance,
                                           @RequestParam(value = "count", required = false) Integer count) {

        log.info("Try to find nearest stores of location with lat: {}, lon: {}, distance: {} and count: {}", latitude,
                longitude, distance, count);
        return NearestStoresDTO.builder()
                .storesList(storeService.findNearestStores(createPoint(longitude, latitude),
                        distance, count).stream().map(mapper::toStoreDTO).collect(Collectors.toList()))
                .build();
    }

    private Point createPoint(String lon, String lat) {
        try {
            return new Point(Double.parseDouble(lon), Double.parseDouble(lat));
        } catch (NumberFormatException e) {
            log.error("Error occurred in parsing double value of lat or long");
            throw new BadRequestException("Could not parse lat or long", e);
        }
    }

}
