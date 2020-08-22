package com.myssteriion.blindtest.persistence.dao;

import com.myssteriion.blindtest.model.entity.ProfileEntity;
import com.myssteriion.utils.persistence.dao.IDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DAO for Profile.
 */
@Repository
public interface ProfileDAO extends IDAO<ProfileEntity> {
    
    /**
     * Find by name.
     *
     * @param name the name
     * @return the optional
     */
    Optional<ProfileEntity> findByName(String name);
    
    /**
     * Find a page of profile filtered by search name.
     *
     * @param searchName the search name
     * @param pageable   the page
     * @return a page of profile filtered by search name
     */
    Page<ProfileEntity> findAllByNameContainingIgnoreCase(String searchName, Pageable pageable);
    
}
