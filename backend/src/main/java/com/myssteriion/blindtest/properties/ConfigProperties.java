package com.myssteriion.blindtest.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The mapping with config.properties.
 */
@Configuration
@PropertySource("${SPRING_CONFIG_LOCATION}/musics-blindtest/config.properties")
public class ConfigProperties {
    
    @Value("${maxPlayers}")
    private Integer maxPlayers;
    
    @Value("${minPlayers}")
    private Integer minPlayers;
    
    @Value("${avatars.folderPath}")
    private String avatarsFolderPath;
    
    @Value("${musics.folderPath}")
    private String musicsFolderPath;
    
    
    
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
    
    /**
     * Gets avatarsFolderPath.
     *
     * @return The avatarsFolderPath
     */
    public String getAvatarsFolderPath() {
        return avatarsFolderPath;
    }
    
    /**
     * Gets musicsFolderPath.
     *
     * @return The musicsFolderPath
     */
    public String getMusicsFolderPath() {
        return musicsFolderPath;
    }
    
}
