package com.jumbo.nearestdistance.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends MongoRepository<Store, String> {

    List<Store> findByLocationNear(Point point, Pageable pageable);

    List<Store> findByLocationNear(Point point, Distance distance, Pageable pageable);
}
