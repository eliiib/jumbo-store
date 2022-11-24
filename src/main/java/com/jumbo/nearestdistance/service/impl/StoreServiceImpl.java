package com.jumbo.nearestdistance.service.impl;

import com.jumbo.nearestdistance.config.StoreCountConfig;
import com.jumbo.nearestdistance.dao.Store;
import com.jumbo.nearestdistance.dao.StoreRepository;
import com.jumbo.nearestdistance.exception.LatAndLongNotValidException;
import com.jumbo.nearestdistance.service.StoreService;
import com.jumbo.nearestdistance.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final StoreCountConfig storeCountConfig;

    /**
     * Find nearest stores
     * @param point
     * @param distance
     * @param count if count is null, it will be replaced with default config value
     * @throws LatAndLongNotValidException if point elements is not valid
     * @return List<Store>
     */
    @Override
    public List<Store> findNearestStores(Point point, Double distance, Integer count) {
        log.info("Try to fetch nearest stores from repository");
        ValidationUtil.validateCoordinate(point);

        if (count == null) {
            log.debug("Set store count to default of: {}", storeCountConfig.getDefaultValue());
            count = storeCountConfig.getDefaultValue();
        }
        if (distance != null) {
            log.debug("Try to fetch nearest stores based on desired point and distance");
            return storeRepository.findByLocationNear(point, new Distance(distance, Metrics.KILOMETERS), PageRequest.of(0, count));
        } else {
            log.debug("Try to fetch nearest stores based on desired point");
            return storeRepository.findByLocationNear(point, PageRequest.of(0, count));
        }
    }
}
