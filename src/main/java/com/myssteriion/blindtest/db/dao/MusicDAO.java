package com.myssteriion.blindtest.db.dao;

import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * DAO for MusicDTO.
 */
@Repository
public interface MusicDAO extends PagingAndSortingRepository<MusicDTO, Integer> {

    /**
     * Find by name and theme.
     *
     * @param name           the name
     * @param theme          the theme
     * @param connectionMode the connection mode
     * @return the optional
     */
    Optional<MusicDTO> findByNameAndThemeAndConnectionMode(String name, Theme theme, ConnectionMode connectionMode);

    /**
     * Find by themes.
     *
     * @param themes         the themes
     * @param connectionMode the connection mode
     * @return the musics list
     */
    List<MusicDTO> findByThemeInAndConnectionMode(List<Theme> themes, ConnectionMode connectionMode);

}
