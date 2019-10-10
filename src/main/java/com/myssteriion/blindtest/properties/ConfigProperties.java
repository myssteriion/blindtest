package com.myssteriion.blindtest.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The mapping with config.properties.
 */
@Configuration
@PropertySource("classpath:config.properties")
public class ConfigProperties {

    @Value("${pagination.elements.per.page.avatars}")
    private Integer paginationElementsPerPageAvatars;

    @Value("${pagination.elements.per.page.profiles}")
    private Integer paginationElementsPerPageProfiles;


    
    /**
     * Gets paginationElementsPerPageAvatars.
     *
     * @return The paginationElementsPerPageAvatars.
     */
    public Integer getPaginationElementsPerPageAvatars() {
        return paginationElementsPerPageAvatars;
    }

    /**
     * Gets paginationElementsPerPageProfiles.
     *
     * @return The paginationElementsPerPageProfiles.
     */
    public Integer getPaginationElementsPerPageProfiles() {
        return paginationElementsPerPageProfiles;
    }

}
