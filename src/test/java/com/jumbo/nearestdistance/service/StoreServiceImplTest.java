package com.jumbo.nearestdistance.service;

import com.jumbo.nearestdistance.config.StoreCountConfig;
import com.jumbo.nearestdistance.dao.Store;
import com.jumbo.nearestdistance.dao.StoreRepository;
import com.jumbo.nearestdistance.exception.LatAndLongNotValidException;
import com.jumbo.nearestdistance.service.impl.StoreServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class StoreServiceImplTest {

    @Autowired
    private StoreService storeService;

    @MockBean
    private StoreRepository storeRepository;

    @MockBean
    private StoreCountConfig storeCountConfig;

    @Test
    public void getNearestLocations_filterByPoint() {
        Point desiredPoint = new Point(2.3, 3.4);
        Distance distance = new Distance(2D, Metrics.KILOMETERS);
        Mockito.doReturn(5).when(storeCountConfig).getDefaultValue();
        Mockito.doReturn(getStoreList()).when(storeRepository).findByLocationNear(desiredPoint, PageRequest.of(0, 5));

        List<Store> storeList = storeService.findNearestStores(desiredPoint, null, null);
        Mockito.verify(storeRepository, Mockito.times(1)).findByLocationNear(desiredPoint, PageRequest.of(0, 5));
        Mockito.verify(storeRepository, Mockito.times(0)).findByLocationNear(desiredPoint, distance, PageRequest.of(0, 5));
    }

    @Test
    public void getNearestLocations_filterByPointAndDistance() {
        Point desiredPoint = new Point(2.3, 3.4);
        Distance distance = new Distance(2D, Metrics.KILOMETERS);
        Mockito.doReturn(5).when(storeCountConfig).getDefaultValue();
        Mockito.doReturn(getStoreList()).when(storeRepository).findByLocationNear(desiredPoint, distance, PageRequest.of(0, 5));

        storeService.findNearestStores(desiredPoint, 2D, null);
        Mockito.verify(storeRepository, Mockito.times(0)).findByLocationNear(desiredPoint, PageRequest.of(0, 5));
        Mockito.verify(storeRepository, Mockito.times(1)).findByLocationNear(desiredPoint, distance, PageRequest.of(0, 5));
    }

    @Test
    public void getNearestLocations_filterByDefaultCount_ReturnListWithSizeDefaultCount() {
        Point desiredPoint = new Point(2.3, 3.4);
        Distance distance = new Distance(2D, Metrics.KILOMETERS);
        Mockito.doReturn(7).when(storeCountConfig).getDefaultValue();
        Mockito.doReturn(getStoreList()).when(storeRepository).findByLocationNear(desiredPoint, PageRequest.of(0, 7));

        List<Store> storeList = storeService.findNearestStores(desiredPoint, null, null);
        Assertions.assertThat(storeList.size()).isEqualTo(7);
        Mockito.verify(storeRepository, Mockito.times(1)).findByLocationNear(desiredPoint, PageRequest.of(0, 7));
        Mockito.verify(storeRepository, Mockito.times(0)).findByLocationNear(desiredPoint, null);
        Mockito.verify(storeRepository, Mockito.times(0)).findByLocationNear(desiredPoint, distance, PageRequest.of(0, 7));
    }

    @Test
    public void getNearestLocations_invalidLongitude_throwException() {
        Point desiredPoint = new Point(190.3, 3.4);
        Mockito.doReturn(5).when(storeCountConfig).getDefaultValue();
        Mockito.doReturn(getStoreList()).when(storeRepository).findByLocationNear(desiredPoint, PageRequest.of(0, 5));

        Assertions.assertThatExceptionOfType(LatAndLongNotValidException.class).isThrownBy(
                () -> storeService.findNearestStores(desiredPoint, null, null));
    }

    @Test
    public void getNearestLocations_invalidLatitude_throwException() {
        Point desiredPoint = new Point(170.3, 90.4);
        Mockito.doReturn(5).when(storeCountConfig).getDefaultValue();
        Mockito.doReturn(getStoreList()).when(storeRepository).findByLocationNear(desiredPoint, PageRequest.of(0, 5));

        Assertions.assertThatExceptionOfType(LatAndLongNotValidException.class).isThrownBy(
                () -> storeService.findNearestStores(desiredPoint, null, null));
    }

    @TestConfiguration
    @Import(StoreServiceImpl.class)
    public static class Configuration {}

    private List<Store> getStoreList() {
        Store store1 = Store.builder().addressName("firstStore").location(new GeoJsonPoint(1.1, 1.1)).build();
        Store store2 = Store.builder().addressName("secondStore").location(new GeoJsonPoint(1.1, 1.1)).build();
        Store store3 = Store.builder().addressName("thirdStore").location(new GeoJsonPoint(1.1, 1.1)).build();
        Store store4 = Store.builder().addressName("fourthStore").location(new GeoJsonPoint(1.1, 1.1)).build();
        Store store5 = Store.builder().addressName("fifthStore").location(new GeoJsonPoint(1.1, 1.1)).build();
        Store store6 = Store.builder().addressName("sixthStore").location(new GeoJsonPoint(1.1, 1.1)).build();
        Store store7 = Store.builder().addressName("seventhStore").location(new GeoJsonPoint(1.1, 1.1)).build();


        List<Store> storeList = new ArrayList<>();
        storeList.add(store1);
        storeList.add(store2);
        storeList.add(store3);
        storeList.add(store4);
        storeList.add(store5);
        storeList.add(store6);
        storeList.add(store7);
        return storeList;
    }
}
