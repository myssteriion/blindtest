package com.myssteriion.blindtest.persistence.dao;

import com.myssteriion.blindtest.model.entity.AvatarEntity;
import com.myssteriion.utils.persistence.dao.IDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DAO for Avatar.
 */
@Repository
public interface AvatarDAO extends IDAO<AvatarEntity> {
    
    /**
     * Find by name.
     *
     * @param name the name
     * @return the optional
     */
    Optional<AvatarEntity> findByName(String name);
    
    /**
     * Find a page of Avatar filtered by search name.
     *
     * @param searchName the search name
     * @param pageable   the page
     * @return a page of Avatar filtered by search name
     */
    Page<AvatarEntity> findAllByNameContainingIgnoreCase(String searchName, Pageable pageable);
    
}
