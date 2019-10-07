package com.myssteriion.blindtest.db.dao;

import com.myssteriion.blindtest.model.dto.ProfileDTO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DAO for ProfileDTO.
 */
@Repository
public interface ProfileDAO extends PagingAndSortingRepository<ProfileDTO, Integer> {

	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the optional
	 */
	Optional<ProfileDTO> findByName(String name);

}
