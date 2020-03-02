package com.myssteriion.blindtest.persistence.dao;

import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.utils.persistence.dao.IDAO;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DAO for ProfileStatDTO.
 */
@Repository
public interface ProfileStatDAO extends IDAO<ProfileStatDTO> {
    
    /**
     * Find by profile id.
     *
     * @param profileId the profile id
     * @return the optional
     */
    Optional<ProfileStatDTO> findByProfileId(Integer profileId);
    
}
