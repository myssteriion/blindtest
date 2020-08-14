package com.myssteriion.blindtest.persistence.dao;

import com.myssteriion.blindtest.model.dto.AvatarDTO;
import com.myssteriion.utils.persistence.dao.IDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DAO for AvatarDTO.
 */
@Repository
public interface AvatarDAO extends IDAO<AvatarDTO> {
    
    /**
     * Find by name.
     *
     * @param name the name
     * @return the optional
     */
    Optional<AvatarDTO> findByName(String name);
    
    /**
     * Find a page of Avatar filtered by search name.
     *
     * @param searchName the search name
     * @param pageable   the page
     * @return a page of Avatar filtered by search name
     */
    Page<AvatarDTO> findAllByNameContainingIgnoreCase(String searchName, Pageable pageable);
    
}
