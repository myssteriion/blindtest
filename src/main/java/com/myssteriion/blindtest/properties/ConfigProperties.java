package com.myssteriion.blindtest.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The mapping with config.properties.
 */
@Configuration
@PropertySource("${SPRING_CONFIG_LOCATION}/config.properties")
public class ConfigProperties {

    @Value("${maxPlayers}")
    private Integer maxPlayers;

    @Value("${minPlayers}")
    private Integer minPlayers;



    /**
     * Gets maxPlayers.
     *
     * @return The maxPlayers.
     */
    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Gets minPlayers.
     *
     * @return The minPlayers.
     */
    public Integer getMinPlayers() {
        return minPlayers;
    }

}
