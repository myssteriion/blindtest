package com.myssteriion.blindtest.db.dao;

import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DAO for ProfileStatDTO.
 */
@Repository
public interface ProfileStatDAO extends PagingAndSortingRepository<ProfileStatDTO, Integer> {

    /**
     * Find by profile id.
     *
     * @param profileId the profile id
     * @return the optional
     */
    Optional<ProfileStatDTO> findByProfileId(Integer profileId);

}
