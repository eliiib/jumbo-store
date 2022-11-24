package com.jumbo.nearestdistance.transform;

import com.jumbo.nearestdistance.controller.dto.NearestStoresDTO;
import com.jumbo.nearestdistance.dao.Store;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StoreModelMapper {

    NearestStoresDTO.StoreDTO toStoreDTO(Store store);
}
