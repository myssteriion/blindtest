package com.myssteriion.blindtest.db.dao;

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
     * @param name  the name
     * @param theme the theme
     * @return the optional
     */
    Optional<MusicDTO> findByNameAndTheme(String name, Theme theme);

    /**
     * Find by themes.
     *
     * @param themes the themes
     * @return the musics list
     */
    List<MusicDTO> findByThemeIn(List<Theme> themes);

}
