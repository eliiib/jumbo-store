package com.jumbo.nearestdistance.service;

import com.jumbo.nearestdistance.dao.Store;
import org.springframework.data.geo.Point;

import java.util.List;

public interface StoreService {

    List<Store> findNearestStores(Point point, Double distance, Integer nearestLocationCount);
}
