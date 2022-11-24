package com.jumbo.nearestdistance.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Component
@ConfigurationProperties(prefix = "nearest-stores")
public class StoreCountConfig {
    private int defaultCount;

    public int getDefaultValue() {
        return defaultCount;
    }
}
