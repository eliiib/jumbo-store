package com.jumbo.nearestdistance.controller;


import com.jumbo.nearestdistance.controller.dto.NearestStoresDTO;
import com.jumbo.nearestdistance.dao.Store;
import com.jumbo.nearestdistance.dao.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StoreControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StoreRepository storeRepository;

    @LocalServerPort
    private int serverPort;

    @Container
    public static final MongoDBContainer container = new MongoDBContainer("mongo:5.0");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", container::getReplicaSetUrl);
        registry.add("spring.data.mongodb.database",  () -> "jumbo");
        registry.add("spring.data.mongodb.auto-index-creation",  () -> true);
        registry.add("nearest-stores.default-count",  () -> 5);
    }

    @BeforeEach
    public void init() {
        this.storeRepository.deleteAll();
        this.storeRepository.saveAll(storeList());
    }


    @Test
    public void getNearestDistance_validParam_returnLocations() {
        ResponseEntity<NearestStoresDTO> response = restTemplate.getForEntity(createUrl("/search?lat=2.2&lon=3.5"), NearestStoresDTO.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(Objects.requireNonNull(response.getBody()).getStoresList().size()).isEqualTo(5);
    }


    @Test
    public void getNearestDistance_invalidParams_returnBadRequestException() {
        ResponseEntity<NearestStoresDTO> response = restTemplate.getForEntity(createUrl("/search?lon=3.5"), NearestStoresDTO.class);
        assertThat(response.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    public void getNearestDistance_invalidLatValue_returnException() {
        ResponseEntity<NearestStoresDTO> response = restTemplate.getForEntity(createUrl("/search?lat=98.567433"), NearestStoresDTO.class);
        assertThat(response.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    public void getNearestDistance_withStoresCountFilter_returnListWithSizeEqualToCount() {
        ResponseEntity<NearestStoresDTO> response = restTemplate.getForEntity(createUrl("/search?lat=2&lon=3.5&count=7"), NearestStoresDTO.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(Objects.requireNonNull(response.getBody()).getStoresList().size()).isEqualTo(7);
    }

    private String createUrl(String address) {
        return new StringBuilder("http://localhost:")
                .append(serverPort).append("/jumbo/stores").append(address).toString();
    }

    @TestConfiguration
    public static class Configuration {

        @Bean
        public TestRestTemplate testRestTemplate() {
            return new TestRestTemplate();
        }
    }

    private List<Store> storeList() {
        Store firstStore = Store.builder().uuid("EOgKYx4XFiQAAAFJa_YYZ4At").addressName("firstStore").location(new GeoJsonPoint(4.615551, 51.778461)).build();
        Store secondStore = Store.builder().uuid("7ewKYx4Xqp0AAAFIHigYwKrH").addressName("secondStore").location(new GeoJsonPoint(6.245829, 51.874272)).build();
        Store thirdStore = Store.builder().uuid("gssKYx4XJwoAAAFbn.BMqPTb").addressName("thirdStore").location(new GeoJsonPoint(4.762433, 52.264417)).build();
        Store fourthStore = Store.builder().uuid("Tk0KYx4XZ3YAAAFc_DRE1DKo").addressName("fourthStore").location(new GeoJsonPoint(5.469597, 51.399843)).build();
        Store fifthStore = Store.builder().uuid("0XcKYx4XNRQAAAFI3LgYwKxK").addressName("fifthStore").location(new GeoJsonPoint(6.576066, 51.923993)).build();
        Store sixthStore = Store.builder().uuid("HocKYx4XP6wAAAFM3VBYQRZw").addressName("sixthStore").location(new GeoJsonPoint(3.444601, 51.275006)).build();
        Store seventhStore = Store.builder().uuid("V7cKYx4X0QUAAAFMTmYM5CXj").addressName("seventhStore").location(new GeoJsonPoint(4.749492, 52.645601)).build();

        List<Store> storeList = new ArrayList<>();
        storeList.add(firstStore);
        storeList.add(secondStore);
        storeList.add(thirdStore);
        storeList.add(fourthStore);
        storeList.add(fifthStore);
        storeList.add(sixthStore);
        storeList.add(seventhStore);
        return storeList;
    }
}