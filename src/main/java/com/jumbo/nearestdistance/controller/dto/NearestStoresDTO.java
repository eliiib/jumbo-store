package com.jumbo.nearestdistance.controller.dto;

import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NearestStoresDTO {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StoreDTO {
        private String uuid;
        private String city;
        private String todayOpen;
        private String sapStoreID;
        private String todayClose ;
        private String street;
        private String street2;
        private String street3;
        private String addressName;
    }

    private List<StoreDTO> storesList;
}
