package com.myssteriion.blindtest.db.dao;

import com.myssteriion.blindtest.model.dto.AvatarDTO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DAO for MusicDTO.
 */
@Repository
public interface AvatarDAO extends PagingAndSortingRepository<AvatarDTO, Integer> {

    /**
     * Find by name.
     *
     * @param name the name
     * @return the optional
     */
    Optional<AvatarDTO> findByName(String name);

}
